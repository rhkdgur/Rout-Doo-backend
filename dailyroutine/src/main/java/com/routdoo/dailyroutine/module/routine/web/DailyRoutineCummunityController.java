package com.routdoo.dailyroutine.module.routine.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.routine.dto.*;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineCommentService;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import com.routdoo.dailyroutine.module.routine.service.RoutineRangeConfigType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.ConditionalFormattingThreshold;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.web
 * fileName       : DailyRoutineCummunityController
 * author         : rhkdg
 * date           : 2023-12-04
 * description    : 공개된 일정 커뮤니티 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-04        rhkdg       최초 생성
 */
@Tag(name="공개된 일정 커뮤니티 컨트롤러")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class DailyRoutineCummunityController extends BaseModuleController {

    private final DailyRoutineService dailyRoutineService;

    private final DailyRoutineCommentService dailyRoutineCommentService;

    private final MemberService memberService;

    private final MemberSession memberSession;

    /**
     * 일정 중 제일많은 태그 조회
     * @return
     * @throws Exception
     */
    @Operation(summary = "일정 중 제일 많은 태그 목록 조회",description = "파라미터 따로 요청안함")
    @GetMapping(API_URL+"/daily/routine/most/tag/list")
    public Map<String,Object> selectDailyRoutineTagMostList() throws Exception {

        List<Map<String,Object>> resultList = dailyRoutineService.selectDailyRoutineTagMostList();
        modelMap.put("tagList",resultList);

        return modelMap;
    }

    /**
     * 공개 일정 목록
     * @param sstring
     * @return
     * @throws Exception
     */
    @Operation(summary = "공개 일정 목록 조회")
    @Parameter(name="sstring", description = "검색어")
    @GetMapping(API_URL+"/daily/routine/plan/list")
    public Map<String,Object> selectDailyRoutinePlanList(@RequestParam(value = "sstring",defaultValue = "") String sstring,
                                                         @RequestParam(value = "cpage",defaultValue =  "1") int page) throws Exception{

        DailyRoutineDefaultDto searchDto = new DailyRoutineDefaultDto();
        searchDto.setSstring(sstring);
        searchDto.setPage(page);
        searchDto.setRangeType(RoutineRangeConfigType.PUBLIC.name());
        searchDto.setMemberId(memberSession.isAuthenticated() ? memberSession.getMemberSession().getId() : "");
        searchDto.setCummunity(true);
        searchDto.setFullTextSearch(true);

        Page<DailyRoutineDto> resultList = dailyRoutineService.selectDailyRoutinePageList(searchDto);

        modelMap.put("dailyList",resultList.getContent().stream().map(DailyRoutineDto::toSummaryMap).toList());
        modelMap.put("pageable",resultList.getPageable());
        modelMap.put("totalElements",resultList.getTotalElements());
        modelMap.put("totalPages",resultList.getTotalPages());

        return modelMap;
    }

    /**
     * 일정 상세 정보
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "일정 상세 정보" , description = "회원 요약 정보, 일정 정보, 일정 타임라인, 댓글 정보 가져옵니다.")
    @Parameter(name="idx" ,description = "일정 일련번호")
    @GetMapping(API_URL+"/daily/routine/plan/view")
    public Map<String,Object> selectDailyRoutinePlanView(@RequestParam("idx") Long idx ) throws Exception {

        DailyRoutineDto dailyRoutineDto = new DailyRoutineDto();
        dailyRoutineDto.setIdx(idx);

        dailyRoutineDto = dailyRoutineService.selectDailyRoutineView(dailyRoutineDto);

        MemberDto memberDto = new MemberDto();
        memberDto.setId(dailyRoutineDto.getMemberId());
        memberDto = memberService.selectMember(memberDto);

        //좋아요 여부 확인
        DailyRoutineLikeDefaultDto likeDto = new DailyRoutineLikeDefaultDto();
        likeDto.setDailyIdx(idx);
        likeDto.setMemberId(memberSession.getMemberSession().getId());
        Page<Map<String,Object>> likeList = dailyRoutineService.selectDailyRoutineLikePageList(likeDto);
        String likeYn = "N";
        if(likeList.getTotalElements() > 0){
            likeYn = "Y";
        }

        modelMap.put("memberDto",memberDto.getSummaryInfo());
        modelMap.put("dailyRoutine",dailyRoutineDto.toSummaryMap());
        modelMap.put("likeYn",likeYn);

        return modelMap;
    }

    @Operation(summary="일정 상세 정보 타임라인 목록 조회")
    @Parameter(name = "dailyIdx", description = "스케줄 부모 idx")
    @GetMapping(API_URL+"/daily/routine/plan/time/line/list")
    public Map<String,Object> selectDailyRoutinePlanTimeLineList(@RequestParam("dailyIdx") Long dailyIdx) throws Exception {

        //일정 타임라인 정보 조회
        DailyRoutineTimeLineDefaultDto searchDto = new DailyRoutineTimeLineDefaultDto();
        searchDto.setDailyIdx(dailyIdx);
        List<DailyRoutineTimeLineDto> list = dailyRoutineService.selectDailyRoutineTimeLineList(searchDto);
        List<Map<String,Object>> resultList = list.stream().map(DailyRoutineTimeLineDto::toMap).toList();

        int totalCost = 0;
        for(Map<String,Object> map : resultList) {
            totalCost += (int)map.get("cost");
        }

        //날짜별 정렬하여 전달
        TreeMap<String,List<Map<String,Object>>> resultMap = resultList.stream().collect(Collectors.groupingBy(x-> x.get("applyDate").toString(),
                TreeMap::new,
                Collectors.toList()));

        //일정 타임라인 정보
        modelMap.put("timeList", resultMap);
        modelMap.put("totalCost",totalCost);

        return modelMap;
    }

    @Operation(summary = "공개 일정 댓글 목록" , description = "일정에 대한 댓글 정보 목록을 가져옵니다.")
    @Parameter(name="dailyIdx" ,description = "일정 일련번호")
    @GetMapping(API_URL+"/daily/routine/comment/list")
    public Map<String,Object> selectDailyRoutineCommentList(@RequestParam("dailyIdx") Long idx,
                                                            @RequestParam(value="cpage",defaultValue = "1") int page) throws Exception {

        //코멘트 조회
        DailyRoutineCommentDefaultDto commentDto = new DailyRoutineCommentDefaultDto();
        commentDto.setDailyIdx(idx);
        commentDto.setPage(page);
        Page<DailyRoutineCommentDto> commentDtos = dailyRoutineCommentService.selectDailyRoutineCommentPageList(commentDto);

        Map<String,Object> commentMap = new LinkedHashMap<>();
        String memberId = memberSession.isAuthenticated() ? memberSession.getMemberSession().getId() : "";

        List<Map<String,Object>> commentDtoList = new ArrayList<>();
        for(DailyRoutineCommentDto dto : commentDtos){
            commentDtoList.add(dto.toSummaryMap(memberId));
        }

        commentMap.put("content",commentDtoList);
        commentMap.put("totalElements",commentDtos.getTotalElements());
        commentMap.put("totalPages",commentDtos.getTotalPages());
        commentMap.put("pageable",commentDtos.getPageable());

        modelMap.put("commentList",commentMap);

        return modelMap;
    }

    /**
     * 답글 목록
     * @param idx
     * @param page
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 목록 조회")
    @Parameters({
            @Parameter(name="commentIdx", description = "댓글 일련번호"),
            @Parameter(name="cpage", description = "페이지 번호")
    })
    @GetMapping(API_URL+"/daily/routine/reply/comment")
    public Map<String,Object> selectDailyRoutineReplyComment(@RequestParam("commentIdx") Long idx,
                                                             @RequestParam(value="cpage",defaultValue = "1") int page) throws Exception {

        DailyRoutineReplyCommentDto replyCommentDto = new DailyRoutineReplyCommentDto();
        replyCommentDto.setCommentIdx(idx);
        replyCommentDto.setPage(page);

        Page<DailyRoutineReplyCommentDto> replyCommentDtos = dailyRoutineCommentService.selectDailyRoutineReplyCommentPageList(replyCommentDto);

        String memberId = memberSession.isAuthenticated() ? memberSession.getMemberSession().getId() : "";

        for(DailyRoutineReplyCommentDto tmp : replyCommentDtos){
            tmp.addCheckUser(memberId);
        }

        modelMap.put("replayList",replyCommentDtos);

        return modelMap;
    }


    /**
     * 댓글 등록
     * @param dailyRoutineCommentDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 등록")
    @Parameters(value= {
            @Parameter(name = "dailyIdx", description = "일정 일련번호"),
            @Parameter(name = "context", description = "내용")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "400", description = "등록 오류"),
            @ApiResponse(responseCode = "422", description = "등록 실패")
    })
    @PostMapping(API_URL+"/daily/routine/comment/ins")
    public ResponseEntity<String> insertDailyRoutineComment(final @Valid @RequestBody  DailyRoutineCommentDto dailyRoutineCommentDto) throws Exception {

        try{
            dailyRoutineCommentDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = dailyRoutineCommentService.insertDailyRoutineComment(dailyRoutineCommentDto);
            if(!result){
                return new ResponseEntity<>("등록에 실패하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("### insert daily routine comment error : {}",e.getMessage());
            return new ResponseEntity<>("등록시 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("등록 되었습니다.",HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 수정")
    @Parameters(value= {
            @Parameter(name= "idx", description = "댓글 일련번호"),
            @Parameter(name = "dailyIdx", description = "일정 일련번호"),
            @Parameter(name = "context", description = "내용")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "422", description = "수정 실패"),
            @ApiResponse(responseCode = "400", description = "수정 오류")
    })
    @PutMapping(API_URL+"/daily/routine/comment/upd")
    public ResponseEntity<String> updateDailyRoutineComment(final @Valid @RequestBody DailyRoutineCommentDto dailyRoutineCommentDto) throws Exception {

        try{
            dailyRoutineCommentDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = dailyRoutineCommentService.updateDailyRoutineComment(dailyRoutineCommentDto);
            if(!result){
                return new ResponseEntity<>("수정에 실패하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### update daily routine comment error : {}",e.getMessage());
            return new ResponseEntity<>("수정시 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("수정 되었습니다.",HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 삭제")
    @Parameter(name="idx", description = "댓글 일련번호")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "삭제 완료"),
            @ApiResponse(responseCode = "400", description = "삭제 오류"),
            @ApiResponse(responseCode = "422", description = "삭제 실패")
    })
    @DeleteMapping(API_URL+"/daily/routine/comment/del")
    public ResponseEntity<String> deleteDailyRoutineComment(@RequestParam("idx") Long idx) throws Exception {

        try{
            DailyRoutineCommentDto dto = new DailyRoutineCommentDto();
            dto.setIdx(idx);
            boolean result = dailyRoutineCommentService.deleteDailyRoutineComment(dto);
            if(!result){
                return new ResponseEntity<>("삭제에 실패하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### delete daily routine comment error : {}",e.getMessage());
            return new ResponseEntity<>("삭제시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
    }

    /**
     * 답글 등록
     * @param dailyRoutineReplyCommentDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 등록")
    @Parameters(value={
            @Parameter(name = "commentIdx", description = "댓글 일련번호"),
            @Parameter(name = "context", description = "내용")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "422", description = "등록 실패"),
            @ApiResponse(responseCode = "400", description = "등록 오류")
    })
    @PostMapping(API_URL+"/daily/routine/reply/ins")
    public ResponseEntity<String> insertDailyRoutineReplyComment(final @Valid @RequestBody DailyRoutineReplyCommentDto dailyRoutineReplyCommentDto) throws Exception {

        try{
            dailyRoutineReplyCommentDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = dailyRoutineCommentService.insertDailyRoutineReplyComment(dailyRoutineReplyCommentDto);
            if(!result){
                return new ResponseEntity<>("등록에 실패하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### insert daily routine reply comment error : {}",e.getMessage());
            return new ResponseEntity<>("등록시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("등록 되었습니다.",HttpStatus.OK);
    }

    /**
     * 답글 수정
     * @param dailyRoutineReplyCommentDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 수정")
    @Parameters(value={
            @Parameter(name = "idx", description = "답글 일련번호"),
            @Parameter(name = "commentIdx", description = "댓글 일련번호"),
            @Parameter(name = "context", description = "내용")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "422", description = "수정 실패"),
            @ApiResponse(responseCode = "400", description = "수정 오류")
    })
    @PutMapping(API_URL+"/daily/routine/reply/upd")
    public ResponseEntity<String> updateDailyRoutineReplyComment(final @Valid @RequestBody DailyRoutineReplyCommentDto dailyRoutineReplyCommentDto) throws Exception {

        try{
            dailyRoutineReplyCommentDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = dailyRoutineCommentService.updateDailyRoutineReplyComment(dailyRoutineReplyCommentDto);
            if(!result){
                return new ResponseEntity<>("수정에 실패하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### update daily routine reply comment error : {}",e.getMessage());
            return new ResponseEntity<>("수정시 오류가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>("수정 되었습니다.",HttpStatus.OK);
    }

    /**
     * 답글 삭제
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 삭제")
    @Parameter(name="idx", description = "답글 일련번호")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "삭제 완료"),
            @ApiResponse(responseCode = "422", description = "삭제 실패"),
            @ApiResponse(responseCode = "400", description = "삭제 오류")
    })
    @DeleteMapping(API_URL+"/daily/routine/reply/del")
    public ResponseEntity<String> deleteDailyRoutineReplyComment(@RequestParam("idx") Long idx) throws Exception {

        try{
            DailyRoutineReplyCommentDto replyCommentDto = new DailyRoutineReplyCommentDto();
            replyCommentDto.setIdx(idx);
            boolean result = dailyRoutineCommentService.updateDailyRoutineReplyComment(replyCommentDto);
            if(!result){
                return new ResponseEntity<>("삭제에 실패하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### delete daily routine reply comment error : {}",e.getMessage());
            return new ResponseEntity<>("삭제시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
    }

}
