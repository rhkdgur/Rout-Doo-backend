package com.routdoo.dailyroutine.module.routine.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action
 * fileName       : DailyRoutineDeleteRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 일상 삭제 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "일상 삭제 request")
@Getter
@Setter
public class DailyRoutineDeleteRequest {
    @NotNull(message = "일련번호는 필수값입니다.")
    private long idx = 0L;
}
