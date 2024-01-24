package com.routdoo.dailyroutine.module.place.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.web.BaseController;
import com.routdoo.dailyroutine.module.place.dto.PlaceCommentDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceReplyCommentDto;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineComment;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineReplyCommentDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.web
 * fileName       : PlaceCommentController
 * author         : rhkdg
 * date           : 2024-01-24
 * description    : 놀거리 댓글 관리자 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-24        rhkdg       최초 생성
 */
@Tag(name="관리자 놀거리 댓글 컨트롤러")
@RestController
@RequiredArgsConstructor
public class PlaceCommentController extends BaseController {

    private final PlaceService placeService;

    private final DailyRoutineCommentService dailyRoutineCommentService;

    private final MemberSession memberSession;

    
    @Operation(summary = "댓글 목록 조회")
    @Parameter(name = "cpage" , description = "페이지 번호")
    @GetMapping(MGN_URL+"/comment/list")
    public Map<String,Object> selectCommentList(@RequestParam Map<String,String> paramUtil) throws Exception {

        modelMap = new LinkedHashMap<>();

        Page<Map> resultList = dailyRoutineCommentService.selectCommentPageList(paramUtil);
        modelMap.put("resultList", resultList);

        return modelMap;
    }

    @Operation(summary = "답글 목록 조회")
    @Parameters(value={
            @Parameter(name = "idx", description = "일련번호"),
            @Parameter(name = "gubun", description = "놀거리, 일정 구분")
    })
    @GetMapping(MGN_URL+"/comment/view")
    public Map<String,Object> selectCommentReplyList(@RequestParam Map<String,String> paramUtil) throws Exception {

        modelMap = new LinkedHashMap<>();

        if(paramUtil.get("gubun").equals("일정")){

            DailyRoutineCommentDto commentDto = new DailyRoutineCommentDto();
            commentDto.setIdx(Long.valueOf(paramUtil.get("idx")));
            commentDto = dailyRoutineCommentService.selectDailyRoutineComment(commentDto);

            DailyRoutineReplyCommentDto commentReplyDto = new DailyRoutineReplyCommentDto();
            commentReplyDto.setCommentIdx(commentDto.getIdx());
            List<DailyRoutineReplyCommentDto> replyList = dailyRoutineCommentService.selectDailyRoutineReplyCommentList(commentReplyDto);

            modelMap.put("commentDto", commentDto);
            modelMap.put("replyList", replyList);

        }else if(paramUtil.get("gubun").equals("놀거리")){

            PlaceCommentDto commentDto = new PlaceCommentDto();
            commentDto.setIdx(Long.valueOf(paramUtil.get("idx")));
            commentDto = placeService.selectPlaceCommentView(commentDto);

            PlaceDefaultDto placeDefaultDto = new PlaceDefaultDto();
            placeDefaultDto.setCommentIdx(commentDto.getIdx());
            List<PlaceReplyCommentDto> replyList = placeService.selectPlaceReplyCommentList(placeDefaultDto);

            modelMap.put("commentDto", commentDto);
            modelMap.put("replyList", replyList);
        }

        return modelMap;
    }


    @Operation(summary = "답글 삭제")
    @Parameters(value={
            @Parameter(name = "idx", description = "일련번호"),
            @Parameter(name = "gubun", description = "놀거리, 일정 구분")
    })
    @GetMapping(MGN_URL+"/comment/reply/del")
    public ResponseEntity<String> deleteCommentReply(@RequestParam("idx") long idx ,
                                                     @RequestParam("gubun") String gubun) throws Exception {

        if(gubun.equals("일정")){
            try{
                DailyRoutineReplyCommentDto replyCommentDto = new DailyRoutineReplyCommentDto();
                replyCommentDto.setIdx(idx);
                boolean result = dailyRoutineCommentService.updateDailyRoutineReplyComment(replyCommentDto);
                if(!result){
                    return new ResponseEntity<>("삭제에 실패하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }catch (Exception e) {
                logger.error("### delete daily routine reply comment error : {}", e.getMessage());
                return new ResponseEntity<>("삭제시 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
            }
        }else if(gubun.equals("놀거리")){
            try{
                PlaceReplyCommentDto dto = new PlaceReplyCommentDto();
                dto.setIdx(idx);
                dto.getMemberDto().setId(memberSession.getMemberSession().getId());
                boolean result = placeService.deletePlaceReplyComment(dto);
                if(result){
                    return new ResponseEntity<>("답글삭제요청이 진행되지않았습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }catch(Exception e){
                logger.error("### delete place comment reply error : {}",e.getMessage());
                return new ResponseEntity<>("답글삭제요청시 이슈가발생했습니다.",HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
    }
}
