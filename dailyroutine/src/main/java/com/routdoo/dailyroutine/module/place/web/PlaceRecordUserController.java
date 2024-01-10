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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin("*")
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
    @Parameters(value={
            @Parameter(name = "memberId", description = "회원 아이디"),
            @Parameter(name = "placeNum", description = "장소 일련번호"),
            @Parameter(name = "tel", description = "연락처"),
            @Parameter(name = "addr", description = "주소"),
            @Parameter(name = "mapx", description = "경도"),
            @Parameter(name = "mapy", description = "위도"),
            @Parameter(name = "useInfo", description = "이용안내"),
            @Parameter(name = "detailText", description = "상세 내용"),
            @Parameter(name = "useType", description = "사용여부 ex) Y, N")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "정보 수정 제안 신청 완료"),
            @ApiResponse(responseCode = "400", description = "정보 수정 제안 신청 오류"),
            @ApiResponse(responseCode = "422", description = "정보 수정 제안 신청 실패")
    })
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
            return new ResponseEntity<>("정보 수정 제안시 오류가 발생하였습니다.",HttpStatus.BAD_REQUEST);
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
    @Parameters(value={
            @Parameter(name = "idx", description = "장소 정보 수정 일련번호"),
            @Parameter(name = "memberId", description = "회원 아이디"),
            @Parameter(name = "placeNum", description = "장소 일련번호"),
            @Parameter(name = "tel", description = "연락처"),
            @Parameter(name = "addr", description = "주소"),
            @Parameter(name = "mapx", description = "경도"),
            @Parameter(name = "mapy", description = "위도"),
            @Parameter(name = "useInfo", description = "이용안내"),
            @Parameter(name = "detailText", description = "상세 내용"),
            @Parameter(name = "useType", description = "사용여부 ex) Y, N")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "정보 수정 제안 신청 수정 완료"),
            @ApiResponse(responseCode = "400", description = "정보 수정 제안 신청 수정 오류"),
            @ApiResponse(responseCode = "422", description = "정보 수정 제안 신청 수정 실패")
    })
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
            return new ResponseEntity<>("정보 수정 제안시 오류가 발생하였습니다.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("정보 수정 제안 신청되었습니다.",HttpStatus.OK);
    }

    /**
     * 정보 삭제 요청
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 정보 삭제 요청")
    @Parameter(name="idx", description = "장소 정보 일련번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 요청 완료"),
            @ApiResponse(responseCode = "400", description = "삭제 요청 오류"),
            @ApiResponse(responseCode = "422", description = "삭제 요청 실패")
    })
    @GetMapping(API_URL+"/place/record/remove")
    public ResponseEntity<String> selectPlaceRecordView(@RequestParam("idx") Long idx) throws Exception {
        try{
            PlaceRecordRemoveDto placeRecordRemoveDto = new PlaceRecordRemoveDto();
            placeRecordRemoveDto.setIdx(idx);
            placeRecordRemoveDto.setMemberId(memberSession.getMemberSession().getId());
            boolean result = placeRecordService.deletePlaceRecordRemove(placeRecordRemoveDto);
            if(!result){
                return new ResponseEntity<>("삭제 요청에 실패하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e ){
            logger.error("### place record remove request error : {}",e.getMessage());
            return new ResponseEntity<>("삭제 요청시 오류가 발생하였습니다.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("삭제 요청 되었습니다.",HttpStatus.OK);
    }
}
