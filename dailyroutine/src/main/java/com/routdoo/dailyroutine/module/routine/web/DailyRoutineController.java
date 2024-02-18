package com.routdoo.dailyroutine.module.routine.web;

import com.routdoo.dailyroutine.common.web.BaseController;
import com.routdoo.dailyroutine.module.routine.RoutineResultCodeType;
import com.routdoo.dailyroutine.module.routine.RoutineServiceResult;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.web
 * fileName       : DailyRoutineController
 * author         : rhkdg
 * date           : 2024-01-14
 * description    : 일정 관리 Controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-14        rhkdg       최초 생성
 */
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class DailyRoutineController extends BaseController {

    private final DailyRoutineService dailyRoutineService;


    /**
     * 일정 목록 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/daily/routine/list")
    public Map<String,Object> selectDailyRoutinePageList(DailyRoutineDefaultDto searchDto) throws Exception {

            Page<DailyRoutineDto> resultList = dailyRoutineService.selectDailyRoutinePageList(searchDto);
            modelMap.put("searchDto", searchDto);
            modelMap.put("resultList", resultList);

        return modelMap;
    }

    /**
     * 일정 상세 조회
     * @param idx
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/daily/routine/view")
    public Map<String,Object> selectDailyRoutineView(@RequestParam("idx") Long idx) throws Exception {

        DailyRoutineDto dailyRoutineDto = new DailyRoutineDto();
        dailyRoutineDto.setIdx(idx);
        dailyRoutineDto = dailyRoutineService.selectDailyRoutineView(dailyRoutineDto);
        modelMap.put("dailyRoutine", dailyRoutineDto);

        DailyRoutineInviteDto inviteDto = new DailyRoutineInviteDto();
        inviteDto.setDailyIdx(idx);
        List<DailyRoutineInviteDto> inviteDtoList = dailyRoutineService.selectDailyRoutineInviteList(inviteDto);
        modelMap.put("inviteList",inviteDtoList);

        return modelMap;
    }

    /**
     * 스케줄 명
     * @param dailyRoutineDto
     * @return
     * @throws Exception
     */
    @Operation(summary="일정 등록" ,description = "타임라인 등록전 대표일정 등록")
    @Parameters( value = {
            @Parameter(name = "idx", description ="일련번호"),
            @Parameter(name = "memberId", description ="회원 아이디"),
            @Parameter(name = "title", description ="제목"),
            @Parameter(name = "startdate", description ="시작일자"),
            @Parameter(name = "endDate", description ="제목(장소)"),
            @Parameter(name = "tag", description ="태그"),
            @Parameter(name = "dayType", description ="일정 타입")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "422", description = "등록이 이루어지지 않음"),
            @ApiResponse(responseCode = "400", description = "등록 오류 발생")
    })
    @PostMapping(MGN_URL+"/daily/routine/ins")
    public ResponseEntity<String> insertDailyRoutine(DailyRoutineDto dailyRoutineDto) throws Exception {
        RoutineServiceResult<?> result = null;
        try {
            result = dailyRoutineService.insertDailyRoutine(dailyRoutineDto);
            if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
                return new ResponseEntity<String>(result.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e) {
            logger.error("insert daily routine info and timeline error : {}",e.getMessage());
            return new ResponseEntity<String>("등록시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
    }

    /**
     * 스케줄 삭제 및 타임라인 삭제
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary="스케줄명 및 타임라인 일괄 삭제")
    @Parameter(name = "idx", description = "일련번호")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "삭제 완료"),
            @ApiResponse(responseCode = "422", description = "삭제가 이루어지지 않음"),
            @ApiResponse(responseCode = "400", description = "삭제 오류"),
    })
    @PostMapping(MGN_URL+"/daily/routine/del")
    public ResponseEntity<String> deleteDailyRoutine(@RequestParam("idx") Long idx) throws Exception {

        RoutineServiceResult<?> result = null;
        try {

            DailyRoutineDto dto = new DailyRoutineDto();
            dto.setIdx(idx);
            result = dailyRoutineService.deleteDailyRoutine(dto);
            if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
                return new ResponseEntity<String>(result.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e) {
            logger.error("delete daily routine error : {}",e.getMessage());
            return new ResponseEntity<String>("삭제시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
    }


    /**
     * 타임라인별 등록
     * @return
     * @throws Exception
     */
    @Operation(summary="타임라인별 등록")
    @Parameters( value = {
            @Parameter(name = "dailyIdx", description ="부모 일련번호"),
            @Parameter(name = "writeType", description ="작성타입"),
            @Parameter(name = "applyDate", description ="적용일자"),
            @Parameter(name = "title", description ="제목"),
            @Parameter(name = "placeName", description="장소명"),
            @Parameter(name="addr", description = "주소"),
            @Parameter(name="mapx", description = "경도"),
            @Parameter(name="mapy", description = "위도"),
            @Parameter(name = "ord", description ="순서"),
            @Parameter(name = "context", description ="내용"),
            @Parameter(name = "shour", description ="시작시간"),
            @Parameter(name = "smin", description ="시작분"),
            @Parameter(name = "ehour", description ="마지막시간"),
            @Parameter(name = "emin", description ="마지막분"),
            @Parameter(name = "cost", description ="비용")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "422", description = "등록 이루어지지 않음"),
            @ApiResponse(responseCode = "400", description = "등록 오류 발생")
    })
    @PostMapping(value=MGN_URL+"/daily/routine/time/line/act/ins")
    public ResponseEntity<String> insertDailyRoutineTimeLine(DailyRoutineTimeLineDto dailyRoutineTimeLineDto) throws Exception {

        try {

            if(!dailyRoutineService.insertDailyRoutineTimeLine(dailyRoutineTimeLineDto)) {
                return new ResponseEntity<String>("등록 되지 않았습니다. 다시 진행해주시기 바랍니다.",HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e) {
            logger.error("insert daily routine time line error : {}",e.getMessage());
            return new ResponseEntity<String>("등록시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("등록 되었습니다.",HttpStatus.OK);
    }

    /**
     * 타임라인별 수정
     * @param dailyRoutineTimeLineDto
     * @return
     * @throws Exception
     */
    @Operation(summary="타임라인별 수정")
    @Parameters(value={
            @Parameter(name = "idx", description = "일련번호"),
            @Parameter(name = "dailyIdx", description="부모 일련번호"),
            @Parameter(name = "writeType", description="작성타입"),
            @Parameter(name = "applyDate", description="적용일자"),
            @Parameter(name = "title", description="제목"),
            @Parameter(name = "placeName", description="장소명"),
            @Parameter(name="addr", description = "주소"),
            @Parameter(name="mapx", description = "경도"),
            @Parameter(name="mapy", description = "위도"),
            @Parameter(name = "ord", description="순서"),
            @Parameter(name = "context", description="내용"),
            @Parameter(name = "shour", description="시작시간"),
            @Parameter(name = "smin", description="시작분"),
            @Parameter(name = "ehour", description="마지막시간"),
            @Parameter(name = "emin", description="마지막분"),
            @Parameter(name = "cost", description="비용")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "422", description = "수정이 이루어지지 않음"),
            @ApiResponse(responseCode = "400", description = "수정 오류")
    })
    @PostMapping(value=MGN_URL+"/daily/routine/time/line/act/upd")
    public ResponseEntity<String> updateDailyRoutineTimeLine(DailyRoutineTimeLineDto dailyRoutineTimeLineDto) throws Exception {

        RoutineServiceResult<?> result = null;
        try {

            result = dailyRoutineService.updateDailyRoutineTimeLine(dailyRoutineTimeLineDto);
            if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
                return new ResponseEntity<String>(result.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e) {
            logger.error("update daily routine timeline error : {}",e.getMessage());
            return new ResponseEntity<String>("수정시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
    }

    /**
     * 타임라인별 삭제
     * @param idx
     * @return
     * @throws Exception
     */
    @Operation(summary="타임라인별 삭제")
    @Parameter(name = "idx", description="일련번호")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "삭제 완료"),
            @ApiResponse(responseCode = "422", description = "삭제 오류")
    })
    @PostMapping(value=MGN_URL+"/daily/routine/time/line/act/del")
    public ResponseEntity<String> deleteDailyRoutineTimeLine(@RequestParam("idx") Long idx) throws Exception {

        try {
            DailyRoutineTimeLineDto dto = new DailyRoutineTimeLineDto();
            dto.setIdx(idx);
            dailyRoutineService.deleteDailyRoutineTimeLine(dto);
        }catch (Exception e) {
            logger.error("delete daily timeline error : {}",e.getMessage());
            return new ResponseEntity<String>("삭제시 오류가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<String>("삭제 되었습니다.",HttpStatus.OK);
    }
}
