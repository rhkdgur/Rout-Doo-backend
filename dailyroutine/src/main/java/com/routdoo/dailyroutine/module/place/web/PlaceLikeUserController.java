package com.routdoo.dailyroutine.module.place.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.PlaceLikeDto;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.web
 * fileName       : PlaceLikeUserController
 * author         : rhkdg
 * date           : 2023-11-27
 * description    : 장소 좋아요 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-27        rhkdg       최초 생성
 */
@Tag(name = "장소 좋아요 컨트롤러")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class PlaceLikeUserController extends BaseModuleController {

    private final PlaceService placeService;

    private final MemberSession memberSession;


    /**
     * 좋아요 등록
     * @param placeLikeDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 좋아요 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 추가 완료"),
            @ApiResponse(responseCode = "400", description = "좋아요 추가 오류"),
            @ApiResponse(responseCode = "422", description = "좋아요 추가 실패")
    })
    @PostMapping(API_URL+"/place/like/ins")
    public ResponseEntity<String> insertPlaceLike(final @Valid @RequestBody PlaceLikeDto placeLikeDto) throws Exception {

        try{
            placeLikeDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = placeService.insertPlaceLike(placeLikeDto);
            if(!result){
                return new ResponseEntity<>("좋아요 추가가 진행되지않았습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception e){
            logger.error("### insert place like error : {}",e.getMessage());
            return new ResponseEntity<>("좋아요 추가시 이슈가 발생하였습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("좋아요 추가하였습니다.", HttpStatus.OK);
    }

    /**
     * 좋아요 삭제
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 좋아요 삭제")
    @Parameter(name = "idx" ,description = "장소 좋아요 일련번호")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200" , description = "좋아요 삭제 완료"),
            @ApiResponse(responseCode = "422", description = "좋아요 삭제 오류"),
            @ApiResponse(responseCode = "404", description = "좋아요 삭제시 회원 정보 불일치")
    })
    @DeleteMapping(API_URL+"/place/like/del")
    public ResponseEntity<String> deletePlaceLike(@RequestParam("idx") Long idx) throws Exception {
        
        try{
            String memberId = memberSession.getMemberSession().getId();
            PlaceLikeDto dto = new PlaceLikeDto();
            dto.setIdx(idx);
            dto = placeService.selectPlaceLike(dto);
            if(!dto.getMemberId().equals(memberId)){
                return new ResponseEntity<>("해당 회원정보가 일치하지않습니다.",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error("### delete place like error : {}",e.getMessage());
            return new ResponseEntity<>("좋아요 삭제시 이슈가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        return new ResponseEntity<>("좋아요 삭제되었습니다.",HttpStatus.OK);
    }
}
