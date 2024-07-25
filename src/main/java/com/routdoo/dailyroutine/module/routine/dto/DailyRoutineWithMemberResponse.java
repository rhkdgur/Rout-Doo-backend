package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineWithMemberResponse
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 일정 상세 정보 및 회원 정보 Response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
@Setter
public class DailyRoutineWithMemberResponse {

    private DailyRoutineSummaryResponse dailyRoutineSummaryResponse;

    private MemberSummaryResponse memberSummaryResponse;

    public static DailyRoutineWithMemberResponse of(DailyRoutineSummaryResponse dailyRoutineSummaryResponse, MemberSummaryResponse memberSummaryResponse){
        DailyRoutineWithMemberResponse response = new DailyRoutineWithMemberResponse();
        response.setDailyRoutineSummaryResponse(dailyRoutineSummaryResponse);
        response.setMemberSummaryResponse(memberSummaryResponse);
        return response;
    }

}
