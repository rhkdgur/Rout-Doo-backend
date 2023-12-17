package com.routdoo.dailyroutine.module.place.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.PlaceIntroDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordRemoveDto;
import com.routdoo.dailyroutine.module.place.repository.PlaceRecordRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceRepository;
import com.routdoo.dailyroutine.module.place.service.PlaceRecordService;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.web
 * fileName       : PlaceRecordUserController
 * author         : rhkdg
 * date           : 2023-12-15
 * description    : 정보 수정 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-15        rhkdg       최초 생성
 */
@Tag(name = "놀거리 정보 수정 제안 컨트롤러")
@RestController
@RequiredArgsConstructor
public class PlaceRecordUserController extends BaseModuleController {

    private final PlaceService PlaceService;
    
    private final PlaceRecordService placeRecordService;

    private final MemberSession memberSession;

    /**
     * 정보 수정 제안 처리( 등록)
     * @param placeRecordDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "정보 수정 제안 (등록)")
    @PostMapping(API_URL+"/place/record/ins")
    public ResponseEntity<String> insertPlaceRecord(PlaceRecordDto placeRecordDto) throws Exception {

        try{
            placeRecordDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = placeRecordService.insertPlaceRecord(placeRecordDto);
            if(!result){
                return new ResponseEntity<>("정보 수정 제안에 실패하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e) {
            logger.error("### place record insert error : {}",e.getMessage());
            return new ResponseEntity<>("정보 수정 제안시 오류가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>("정보 수정 제안 신청되었습니다.",HttpStatus.OK);
    }

    /**
     * 정보 수정 제안 처리 (수정)
     * @param placeRecordDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "정보 수정 제안 (수정)")
    @PostMapping(API_URL+"/place/record/upd")
    public ResponseEntity<String> updatePlaceRecord(PlaceRecordDto placeRecordDto) throws Exception {
        try{
            placeRecordDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = placeRecordService.updatePlaceRecord(placeRecordDto);
            if(!result){
                return new ResponseEntity<>("정보 수정 제안에 실패하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            logger.error("### place record update error : {}",e.getMessage());
            return new ResponseEntity<>("정보 수정 제안시 오류가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("정보 수정 제안 신청되었습니다.",HttpStatus.OK);
    }

    /**
     * 정보 삭제 요청
     * @param placeRecordRemoveDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 정보 삭제 요청")
    @GetMapping(API_URL+"/place/record/remove")
    public ResponseEntity<String> selectPlaceRecordView(PlaceRecordRemoveDto placeRecordRemoveDto) throws Exception {
        try{
            boolean result = placeRecordService.deletePlaceRecordRemove(placeRecordRemoveDto);
            if(!result){
                return new ResponseEntity<>("삭제 요청에 실패하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e ){
            logger.error("### place record remove request error : {}",e.getMessage());
            return new ResponseEntity<>("삭제 요청시 오류가 발생하였습니다.",HttpStatus.OK);
        }
        return new ResponseEntity<>("삭제 요청 되었습니다.",HttpStatus.OK);
    }
}
