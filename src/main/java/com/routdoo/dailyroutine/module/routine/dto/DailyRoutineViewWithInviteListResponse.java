package com.routdoo.dailyroutine.module.routine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineViewWithInviteListResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : 일정 상세 정보와 초대 목록 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
public class DailyRoutineViewWithInviteListResponse {

    private List<DailyRoutineInviteResponse> inviteList;

    private DailyRoutineDto dailyRoutineDto;

    public static DailyRoutineViewWithInviteListResponse responseViewWithInvites( DailyRoutineDto dto,List<DailyRoutineInviteResponse> list){
        DailyRoutineViewWithInviteListResponse response = new DailyRoutineViewWithInviteListResponse();
        response.setDailyRoutineDto(dto);
        response.setInviteList(list);
        return response;
    }

}
