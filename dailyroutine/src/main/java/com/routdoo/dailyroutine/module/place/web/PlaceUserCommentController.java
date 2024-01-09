package com.routdoo.dailyroutine.module.place.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.PlaceCommentDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceReplyCommentDto;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.web
 * fileName       : PlaceUserCommentController
 * author         : rhkdg
 * date           : 2023-11-24
 * description    : 놀거리 댓글 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-24        rhkdg     최초 생성
 */
@Tag(name="놀거리 댓글 컨트롤러")
@RestController
@RequiredArgsConstructor
public class PlaceUserCommentController extends BaseModuleController {

    private final PlaceService placeService;

    private final MemberSession memberSession;

    /**
     * 댓글 상세 조회
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 상세 조회")
    @Parameter(name = "idx", description = "댓글 일련번호")
    @GetMapping(API_URL+"/place/comment/view/{idx}")
    public Map<String,Object> selectCommentView(@PathVariable("idx") Long idx) throws Exception {

        modelMap = new LinkedHashMap<>();

        PlaceCommentDto beforeDto = new PlaceCommentDto();
        beforeDto.setIdx(idx);
        beforeDto = placeService.selectPlaceCommentView(beforeDto);
        
        //만약에 해당 조회결과 회원과 일치하지 않는 경우 404를 던짐
        if(!beforeDto.getMemberDto().getId().equals(memberSession.getMemberSession().getId())){
            modelMap.put("status",HttpStatus.NOT_FOUND);
            modelMap.put("commentDto","");
            return modelMap;
        }

        modelMap.put("status",HttpStatus.OK);
        modelMap.put("commentDto",beforeDto);
        
        return modelMap;
    }


    /**
     * 댓글 등록
     * @param dto
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 등록")
    @Parameters(value = {
            @Parameter(name = "memberId", description = "회원 아이디"),
            @Parameter(name = "placeNum", description = "장소 일련번호"),
            @Parameter(name = "context", description = "내용")
    })
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "400", description = "등록 오류"),
            @ApiResponse(responseCode = "422", description = "등록 실패")
    })
    @PostMapping(API_URL+"/place/comment/ins")
    public ResponseEntity<String> insertPlaceComment(PlaceCommentDto dto) throws Exception {


        try{
            dto.getMemberDto().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.insertPlaceComment(dto);
            if(!result){
                return new ResponseEntity<>("등록이 진행되지 않았습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("## insert place comment error : {}",e.getMessage());
            return new ResponseEntity<>("등록시 이슈가 발생하였습니다.",HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("등록 되었습니다.",HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * @param dto
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 수정")
    @Parameters(value = {
            @Parameter(name = "idx", description = "댓글 일련번호"),
            @Parameter(name = "memberId", description = "회원 아이디"),
            @Parameter(name = "placeNum", description = "장소 일련번호"),
            @Parameter(name = "context", description = "내용")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "400", description = "수정 오류"),
            @ApiResponse(responseCode = "422", description = "수정 실패")
    })
    @PostMapping(API_URL+"/place/comment/upd")
    public ResponseEntity<String> updatePlaceComment(PlaceCommentDto dto) throws Exception {
        try{
            dto.getMemberDto().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.updatePlaceComment(dto);
            if(!result){
                return new ResponseEntity<>("수정이 진행되지않았습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("## insert place comment error : {}",e.getMessage());
            return new ResponseEntity<>("수정시 이슈가 발생하였습니다.",HttpStatus.BAD_REQUEST);
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
    @Parameter(name = "idx", description = "댓글 일련번호")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "삭제 완료"),
            @ApiResponse(responseCode = "400", description = "삭제 오류"),
            @ApiResponse(responseCode = "422", description = "삭제 실패")
    })
    @PostMapping(API_URL+"/place/comment/del")
    public ResponseEntity<String> deletePlaceComment(@RequestParam("idx") Long idx) throws Exception {
        try{
            PlaceCommentDto dto = new PlaceCommentDto();
            dto.setIdx(idx);
            dto.getMemberDto().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.deletePlaceComment(dto);
            if(!result){
                return new ResponseEntity<>("삭제 진행되지않았습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("## insert place comment error : {}",e.getMessage());
            return new ResponseEntity<>("삭제시 이슈가 발생하였습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
    }


    /**
     * 댓글에 대한 답글 조회
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글에 대한 답글 조회")
    @Parameter(name = "idx", description = "댓글에 대한 답글 일련번호")
    @GetMapping(API_URL+"/place/comment/reply/{idx}")
    public Map<String,Object> selectCommentReplyList(@PathVariable("idx") Long idx) throws Exception {

        modelMap = new LinkedHashMap<>();

        PlaceDefaultDto searchDto = new PlaceDefaultDto();
        searchDto.setCommentIdx(idx);

        List<PlaceReplyCommentDto> list = placeService.selectPlaceReplyCommentList(searchDto);
        List<Map<String,Object>> resultList = new ArrayList<>();

        String memberId = memberSession.isAuthenticated() ? memberSession.getMemberSession().getId() : "";
        for(PlaceReplyCommentDto dto : list){
            resultList.add(dto.toSummaryMap(memberId));
        }

        modelMap.put("replyList", resultList);

        return modelMap;
    }


    /**
     * 답글 수정 페이지 정보
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 상세 조회")
    @Parameter(name = "idx", description = "답글 일련번호")
    @GetMapping(API_URL+"/place/comment/reply/view/{idx}")
    public Map<String,Object> selectCommentReplyView(@PathVariable("idx") Long idx) throws Exception {

        modelMap = new LinkedHashMap<>();

        PlaceReplyCommentDto dto = new PlaceReplyCommentDto();
        dto.setIdx(idx);
        dto = placeService.selectPlaceReplyComment(dto);

        if(!dto.getMemberDto().getId().equals(memberSession.getMemberSession().getId())){
            modelMap.put("status",HttpStatus.NOT_FOUND);
            modelMap.put("replyDto","");
            return modelMap;
        }

        modelMap.put("status",HttpStatus.OK);
        modelMap.put("replyDto",dto);

        return modelMap;
    }

    /**
     * 답글 등록
     * @param dto
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 등록")
    @Parameters(value={
        @Parameter(name = "commentIdx", description = "댓글 일련번호"),
            @Parameter(name = "placeNum", description = "장소 일련번호"),
            @Parameter(name = "memberId", description = "회원 아이디"),
            @Parameter(name = "context", description = "내용")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "답글 등록 완료"),
            @ApiResponse(responseCode = "400", description = "답글 등록 오류"),
            @ApiResponse(responseCode = "422", description = "답글 등록 실패")
    })
    @PostMapping(API_URL+"/place/comment/reply/ins")
    public ResponseEntity<String> insertReplyComment(PlaceReplyCommentDto dto) throws Exception {

        try{
            dto.getMemberDto().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.insertPlaceReplyComment(dto);
            if(result){
                return new ResponseEntity<>("답글등록요청이 진행되지않았습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### insert place comment reply error : {}",e.getMessage());
            return new ResponseEntity<>("답글등록요청시 이슈가발생했습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("등록 되었습니다.",HttpStatus.OK);
    }

    /**
     * 답글 수정
     * @param dto
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 수정")
    @Parameters(value={
            @Parameter(name = "idx", description = "답글 일련번호"),
            @Parameter(name = "commentIdx", description = "댓글 일련번호"),
            @Parameter(name = "placeNum", description = "장소 일련번호"),
            @Parameter(name = "memberId", description = "회원 아이디"),
            @Parameter(name = "context", description = "내용")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "답글 수정 완료"),
            @ApiResponse(responseCode = "400", description = "답글 수정 오류"),
            @ApiResponse(responseCode = "422", description = "답글 수정 실패")
    })
    @PostMapping(API_URL+"/place/comment/reply/upd")
    public ResponseEntity<String> updateReplyComment(PlaceReplyCommentDto dto) throws Exception {
        
        try{
            dto.getMemberDto().setId(memberSession.getMemberSession().getId());    
            boolean result = placeService.updatePlaceReplyComment(dto);
            if(result){
                return new ResponseEntity<>("답글수정요청이 진행되지않았습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### update place comment reply error : {}",e.getMessage());
            return new ResponseEntity<>("답글수정요청시 이슈가발생했습니다.", HttpStatus.BAD_REQUEST);
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
    @Parameter(name = "idx", description = "답글 일련번호")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "답글 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "답글 삭제 오류"),
            @ApiResponse(responseCode = "422", description = "답글 삭제 실패")
    })
    @PostMapping(API_URL+"/place/comment/reply/del")
    public ResponseEntity<String> deleteReplyComment(@RequestParam("idx") Long idx) throws Exception {

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

        return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
    }

}
