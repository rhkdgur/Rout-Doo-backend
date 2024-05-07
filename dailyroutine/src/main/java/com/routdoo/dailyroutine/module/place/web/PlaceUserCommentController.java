package com.routdoo.dailyroutine.module.place.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.exception.handler.NoMatchDataException;
import com.routdoo.dailyroutine.common.vo.CommonResponse;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.*;
import com.routdoo.dailyroutine.module.place.dto.action.comment.PlaceCommentActionRequest;
import com.routdoo.dailyroutine.module.place.dto.action.comment.PlaceCommentDeleteRequest;
import com.routdoo.dailyroutine.module.place.dto.action.reply.PlaceReplyActionRequest;
import com.routdoo.dailyroutine.module.place.dto.action.reply.PlaceReplyDeleteRequest;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@CrossOrigin("*")
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
    public PlaceCommentResponse selectCommentView(@PathVariable("idx") Long idx) throws Exception {

        PlaceCommentDto beforeDto = new PlaceCommentDto();
        beforeDto.setIdx(idx);
        beforeDto = placeService.selectPlaceCommentView(beforeDto);
        
        //만약에 해당 조회결과 회원과 일치하지 않는 경우 404를 던짐
        if(!beforeDto.getMemberDto().getId().equals(memberSession.getMemberSession().getId())){
            throw new NoMatchDataException("해당 댓글은 회원 정보와 불일치 합니다.");
        }

        return PlaceCommentResponse.of(beforeDto);
    }


    /**
     * 댓글 등록
     * @param placeCommentActionRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 등록")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "400", description = "등록 오류"),
            @ApiResponse(responseCode = "422", description = "등록 실패")
    })
    @PostMapping(API_URL+"/place/comment/ins")
    public ResponseEntity<?> insertPlaceComment(final @Valid @RequestBody PlaceCommentActionRequest placeCommentActionRequest) throws Exception {


        try{
            PlaceCommentDto dto = PlaceCommentDto.createOf(placeCommentActionRequest);
            dto.getMemberDto().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.insertPlaceComment(dto);
            if(!result){
                return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("등록이 진행되지 않았습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("## insert place comment error : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("등록시 이슈가 발생하였습니다."),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("등록 되었습니다."),HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * @param placeCommentActionRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "400", description = "수정 오류"),
            @ApiResponse(responseCode = "422", description = "수정 실패")
    })
    @PutMapping(API_URL+"/place/comment/upd")
    public ResponseEntity<?> updatePlaceComment(final @Valid @RequestBody PlaceCommentActionRequest placeCommentActionRequest) throws Exception {
        try{
            PlaceCommentDto dto = PlaceCommentDto.updateOf(placeCommentActionRequest);
            dto.getMemberDto().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.updatePlaceComment(dto);
            if(!result){
                return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("수정이 진행되지않았습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("## insert place comment error : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("수정시 이슈가 발생하였습니다."),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("수정 되었습니다."),HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * @param placeCommentDeleteRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글 삭제")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "삭제 완료"),
            @ApiResponse(responseCode = "400", description = "삭제 오류"),
            @ApiResponse(responseCode = "422", description = "삭제 실패")
    })
    @DeleteMapping(API_URL+"/place/comment/del")
    public ResponseEntity<?> deletePlaceComment(final @Valid @RequestBody PlaceCommentDeleteRequest placeCommentDeleteRequest) throws Exception {
        try{
            PlaceCommentDto dto = new PlaceCommentDto();
            dto.setIdx(placeCommentDeleteRequest.getIdx());
            dto.getMemberDto().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.deletePlaceComment(dto);
            if(!result){
                return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("삭제 진행되지않았습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("## insert place comment error : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("삭제시 이슈가 발생하였습니다."),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("삭제 되었습니다."),HttpStatus.OK);
    }


    /**
     * 댓글에 대한 답글 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "댓글에 대한 답글 조회")
    @Parameters({
            @Parameter(name = "commentIdx", description = "댓글에 대한 답글 일련번호"),
            @Parameter(name = "page", description = "페이지 번호", required = false)
    })
    @GetMapping(API_URL+"/place/comment/reply/{idx}")
    public List<PlaceReplyCommentResponse> selectCommentReplyList(@Parameter(hidden = true) PlaceDefaultDto searchDto) throws Exception {

        List<PlaceReplyCommentResponse> list = placeService.selectPlaceReplyCommentList(searchDto);
        String memberId = memberSession.isAuthenticated() ? memberSession.getMemberSession().getId() : "";
        for(PlaceReplyCommentResponse dto : list){
            dto.checkIsUser(memberId);
        }
        return list;
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
    public PlaceReplyCommentDto selectCommentReplyView(@PathVariable("idx") Long idx) throws Exception {
        PlaceReplyCommentDto dto = new PlaceReplyCommentDto();
        dto.setIdx(idx);
        dto = placeService.selectPlaceReplyComment(dto);
        if(!dto.getMemberSummaryResponse().getId().equals(memberSession.getMemberSession().getId())){
            throw new NoMatchDataException("해당 답글에 대한 회원 정보가 일치하지 않습니다.");
        }
        return dto;
    }

    /**
     * 답글 등록
     * @param placeReplyActionRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 등록")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "답글 등록 완료"),
            @ApiResponse(responseCode = "400", description = "답글 등록 오류"),
            @ApiResponse(responseCode = "422", description = "답글 등록 실패")
    })
    @PostMapping(API_URL+"/place/comment/reply/ins")
    public ResponseEntity<?> insertReplyComment(final @Valid @RequestBody PlaceReplyActionRequest placeReplyActionRequest) throws Exception {

        try{
            PlaceReplyCommentDto placeReplyCommentDto = PlaceReplyCommentDto.createOf(placeReplyActionRequest);
            placeReplyCommentDto.getMemberSummaryResponse().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.insertPlaceReplyComment(placeReplyCommentDto);
            if(result){
                return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("답글등록요청이 진행되지않았습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### insert place comment reply error : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("답글등록요청시 이슈가발생했습니다."),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("등록 되었습니다."),HttpStatus.OK);
    }

    /**
     * 답글 수정
     * @param placeReplyActionRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 수정")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "답글 수정 완료"),
            @ApiResponse(responseCode = "400", description = "답글 수정 오류"),
            @ApiResponse(responseCode = "422", description = "답글 수정 실패")
    })
    @PutMapping(API_URL+"/place/comment/reply/upd")
    public ResponseEntity<?> updateReplyComment(final @Valid @RequestBody PlaceReplyActionRequest placeReplyActionRequest) throws Exception {
        
        try{
            PlaceReplyCommentDto dto = new PlaceReplyCommentDto();
            dto.getMemberSummaryResponse().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.updatePlaceReplyComment(dto);
            if(result){
                return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("답글수정요청이 진행되지않았습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### update place comment reply error : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("답글수정요청시 이슈가발생했습니다."), HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("수정 되었습니다."),HttpStatus.OK);
    }

    /**
     * 답글 삭제
     * @param placeReplyDeleteRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "답글 삭제")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "답글 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "답글 삭제 오류"),
            @ApiResponse(responseCode = "422", description = "답글 삭제 실패")
    })
    @DeleteMapping(API_URL+"/place/comment/reply/del")
    public ResponseEntity<?> deleteReplyComment(final @Valid @RequestBody PlaceReplyDeleteRequest placeReplyDeleteRequest) throws Exception {

        try{
            PlaceReplyCommentDto dto = new PlaceReplyCommentDto();
            dto.setIdx(placeReplyDeleteRequest.getIdx());
            dto.getMemberSummaryResponse().setId(memberSession.getMemberSession().getId());
            boolean result = placeService.deletePlaceReplyComment(dto);
            if(result){
                return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("답글삭제요청이 진행되지않았습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch(Exception e){
            logger.error("### delete place comment reply error : {}",e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("답글삭제요청시 이슈가발생했습니다."),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
    }

}
