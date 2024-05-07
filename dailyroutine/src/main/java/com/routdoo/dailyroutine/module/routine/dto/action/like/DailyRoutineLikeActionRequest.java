package com.routdoo.dailyroutine.module.routine.dto.action.like;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineLikeActionRequest
 * author         : rhkdg
 * date           : 2024-05-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-07        rhkdg       최초 생성
 */
@Getter
@Setter
public class DailyRoutineLikeActionRequest {

    @Schema(description = "일련번호",defaultValue = "0")
    private long idx = 0L;

    @Schema(description = "일상 일련번호", defaultValue = "0")
    @NotBlank(message = "일상 일련번호는 필수값입니다.")
    private long dailyIdx=0L;

}
