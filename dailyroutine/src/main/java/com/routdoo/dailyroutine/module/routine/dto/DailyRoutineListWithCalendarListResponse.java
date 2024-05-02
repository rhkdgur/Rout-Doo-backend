package com.routdoo.dailyroutine.module.routine.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineListResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
public class DailyRoutineListWithCalendarListResponse {

    private List<Map<String,Object>> calendarList;

    private Page<DailyRoutineSummaryResponse> dailyRoutineList;

    /**
     * 일상 정리 목록과 캘린더 목록
     * @param dailyRoutineList
     * @param calendarList
     * @return
     */
    public static DailyRoutineListWithCalendarListResponse of(Page<DailyRoutineSummaryResponse> dailyRoutineList, List<Map<String,Object>> calendarList){
        DailyRoutineListWithCalendarListResponse response = new DailyRoutineListWithCalendarListResponse();
        response.setCalendarList(calendarList);
        response.setDailyRoutineList(dailyRoutineList);
        return response;
    }

}
