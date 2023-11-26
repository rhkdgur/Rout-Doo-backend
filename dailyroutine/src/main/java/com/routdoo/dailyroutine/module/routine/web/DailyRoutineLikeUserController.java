package com.routdoo.dailyroutine.module.routine.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineLikeDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequiredArgsConstructor
public class DailyRoutineLikeUserController extends BaseModuleController {

    private final DailyRoutineService dailyRoutineService;

    private final MemberSession memberSession;


    /**
     * 좋아요 등록
     * @param dailyRoutineLikeDto
     * @return
     * @throws Exception
     */
    @PostMapping(API_URL+"/daily/routine/like/ins")
    public ResponseEntity<String> insertDailyRoutineLike(@RequestBody DailyRoutineLikeDto dailyRoutineLikeDto) throws Exception {

        try{
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
    @PostMapping(API_URL+"/daily/routine/like/del")
    public ResponseEntity<String>  deleteDailyRoutineLike(@RequestParam("idx") Long idx) throws Exception {

        try{
            String memberId = memberSession.getMemberSession().getId();
            DailyRoutineLikeDto dto = new DailyRoutineLikeDto();
            dto.setIdx(idx);
            dto = dailyRoutineService.selectDailyRoutineLike(dto);
            if(!dto.getMemberId().equals(memberId)){
                return new ResponseEntity<>("좋아요 삭제시 회원정보가 일치하지않습니다.",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error("### delete daily routine error : {}",e.getMessage());
            return new ResponseEntity<>("좋아요 삭제시 이슈가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>("좋아요 삭제하였습니다.",HttpStatus.OK);
    }

}
