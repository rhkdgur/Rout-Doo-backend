package com.routdoo.dailyroutine.module.routine.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action
 * fileName       : DailyRoutineChangeRangeTypeRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Getter
@Setter
public class DailyRoutineChangeRangeTypeRequest {
    
    @Schema(description = "일정 일련번호")
    @NotNull(message = "일정 일련번호는 필수값입니다.")
    private long idx = 0L;

    @Schema(description = "일정 일련번호")
    @NotBlank(message = "공개범위는 필수값입니다.")
    private String rangeType = "";
}
