package com.routdoo.dailyroutine.module.routine.repository;

import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteResponse;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.repository
 * fileName       : DailyRoutineInviteCustomRepository
 * author         : rhkdg
 * date           : 2023-12-04
 * description    : 초대인원 custom interface
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-04        rhkdg       최초 생성
 */
public interface DailyRoutineInviteCustomRepository {

    /**
     * 초대인원 조회
     * @param dto
     * @return
     */
    List<DailyRoutineInviteResponse> selectDailyRoutineInviteDtoList(DailyRoutineInviteDto dto);

}
