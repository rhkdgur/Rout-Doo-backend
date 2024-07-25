package com.routdoo.dailyroutine.module.routine.dto.action.like;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action.like
 * fileName       : DailyRoutineLikeDeleteRequest
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
public class DailyRoutineLikeDeleteRequest {
    @Schema(description = "일련번호",defaultValue = "0")
    @NotNull
    private long idx = 0L;
}
