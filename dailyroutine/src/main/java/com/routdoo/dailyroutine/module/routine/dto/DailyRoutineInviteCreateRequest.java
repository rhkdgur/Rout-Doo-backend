package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineInvite;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineInviteActionRequest
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 친구 초대 Request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
@Setter
public class DailyRoutineInviteCreateRequest {

    private long dailyIdx = 0L;

    private String memberId = "";

    public DailyRoutineInvite toCreateEntity() {
        return DailyRoutineInvite.createInvite().dailyRoutineInviteCreateRequest(this).build();
    }

    public static DailyRoutineInviteCreateRequest of(DailyRoutineInviteCreateRequest request) {
        DailyRoutineInviteCreateRequest dto = new DailyRoutineInviteCreateRequest();
        dto.setDailyIdx(request.getDailyIdx());
        dto.setMemberId(request.getMemberId());
        return dto;
    }
}
