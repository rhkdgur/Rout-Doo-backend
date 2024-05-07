package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.module.routine.service.RoutineDayType;
import com.routdoo.dailyroutine.module.routine.service.RoutineRangeConfigType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineSummaryResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : 일상 summary response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineSummaryResponse {

    private long idx = 0L;

    private String memberId = "";

    private String nickname = "";

    private String tag = "";

    private String title = "";

    private String startDate = "";

    private String endDate = "";

    private String dayType = "";

    private String rangeType = "";

    private String likeYn = "";

    public String getDayTypeDisplay() {
        return RoutineDayType.valueOf(this.dayType).getDisplay();
    }

    public String getRangeTypeDisplay() {
        return RoutineRangeConfigType.valueOf(this.rangeType).getDisplay();
    }

    public DailyRoutineSummaryResponse(long idx, String memberId, String nickname,
                                       String tag, String title, String startDate, String endDate,
                                       String dayType, String rangType, String likeIdx) {
        this.idx = idx;
        this.memberId = memberId;
        this.nickname = nickname;
        this.tag = tag;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayType = dayType;
        this.rangeType = rangType;
        this.likeYn = (likeIdx == null ? "N" : "Y");
    }

    public static DailyRoutineSummaryResponse responseOf(DailyRoutineDto dto) {
        DailyRoutineSummaryResponse response = new DailyRoutineSummaryResponse();
        response.setIdx(dto.getIdx());
        response.setMemberId(dto.getMemberId());
        response.setNickname(dto.getNickname());
        response.setTag(dto.getTag());
        response.setTitle(dto.getTitle());
        response.setStartDate(dto.getStartDate());
        response.setEndDate(dto.getEndDate());
        response.setRangeType(dto.getRangeType());
        response.setLikeYn(dto.getLikeYn());
        return response;
    }

}
