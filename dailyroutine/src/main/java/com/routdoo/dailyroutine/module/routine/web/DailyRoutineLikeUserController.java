package com.routdoo.dailyroutine.module.routine.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineLikeDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
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

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.web
 * fileName       : DailyRoutineLikeController
 * author         : rhkdg
 * date           : 2023-11-26
 * description    : 좋아요 처리 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-26        rhkdg       최초 생성
 */
@Tag(name="좋아요 처리 컨트롤러")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class DailyRoutineLikeUserController extends BaseModuleController {

    private final DailyRoutineService dailyRoutineService;

    private final MemberSession memberSession;


    /**
     * 좋아요 등록
     * @return
     * @throws Exception
     */
    @Operation(summary = "좋아요 등록")
    @Parameters(value={
            @Parameter(name = "dailyIdx", description = "일정 일련번호")
    })
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "좋아요 추가 완료"),
            @ApiResponse(responseCode = "422", description = "좋아요 추가 오류")
    })
    @PostMapping(API_URL+"/daily/routine/like/ins")
    public ResponseEntity<String> insertDailyRoutineLike(@RequestParam("dailyIdx") Long dailyIdx) throws Exception {

        try{
            DailyRoutineLikeDto dailyRoutineLikeDto = new DailyRoutineLikeDto();
            dailyRoutineLikeDto.setDailyIdx(dailyIdx);
            dailyRoutineLikeDto.setMemberId(memberSession.getMemberSession().getId());
            dailyRoutineService.insertDailyRoutineLike(dailyRoutineLikeDto);
        }catch(Exception e){
            logger.error("### insert daily routine like error : {}",e.getMessage());
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
    @Operation(summary = "좋아요 삭제")
    @Parameter(name = "idx", description = "좋아요 일련번호")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "좋아요 삭제 완료"),
            @ApiResponse(responseCode = "404", description = "좋아요 삭제시 회원정보 일치 x"),
            @ApiResponse(responseCode = "422", description = "좋아요 삭제 오류")
    })
    @PostMapping(API_URL+"/daily/routine/like/del")
    public ResponseEntity<String>  deleteDailyRoutineLike(@RequestParam("idx") Long idx) throws Exception {

        try{
            String memberId = memberSession.getMemberSession().getId();
            DailyRoutineLikeDto dto = new DailyRoutineLikeDto();
            dto.setIdx(idx);
            dto = dailyRoutineService.selectDailyRoutineLike(dto);
            if(dto == null || !dto.getMemberId().equals(memberId)){
                return new ResponseEntity<>("좋아요 삭제시 회원정보가 일치하지않습니다.",HttpStatus.NOT_FOUND);
            }
            dailyRoutineService.deleteDailyRoutineLike(dto);
        }catch (Exception e){
            logger.error("### delete daily routine error : {}",e.getMessage());
            return new ResponseEntity<>("좋아요 삭제시 이슈가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>("좋아요 삭제하였습니다.",HttpStatus.OK);
    }

}
