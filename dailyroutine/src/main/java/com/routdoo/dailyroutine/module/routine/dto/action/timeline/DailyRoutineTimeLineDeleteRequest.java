package com.routdoo.dailyroutine.module.routine.dto.action.timeline;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action
 * fileName       : DailyRoutineTimeLineDeleteRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "일정 타임라인 삭제 request")
@Getter
@Setter
public class DailyRoutineTimeLineDeleteRequest {
    @Schema(description = "일정 타임라인 일련번호")
    @NotNull
    private long idx = 0L;
}
