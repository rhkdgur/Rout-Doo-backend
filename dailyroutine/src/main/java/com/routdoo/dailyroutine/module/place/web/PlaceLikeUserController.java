package com.routdoo.dailyroutine.module.place.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.PlaceLikeDto;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(API_URL+"/place/like/ins")
    public ResponseEntity<String> insertPlaceLike(@RequestBody PlaceLikeDto placeLikeDto) throws Exception {

        try{
            placeLikeDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = placeService.insertPlaceLike(placeLikeDto);
            if(!result){
                return new ResponseEntity<>("좋아요 추가가 진행되지않았습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception e){
            logger.error("### insert place like error : {}",e.getMessage());
            return new ResponseEntity<>("좋아요 추가시 이슈가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>("좋아요 추가하였습니다.", HttpStatus.OK);
    }

    /**
     * 좋아요 삭제
     * @param idx
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/place/like/del")
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
