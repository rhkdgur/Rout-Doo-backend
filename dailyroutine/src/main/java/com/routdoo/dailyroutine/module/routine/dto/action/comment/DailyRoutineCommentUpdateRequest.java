package com.routdoo.dailyroutine.module.routine.dto.action.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action.comment
 * fileName       : DailyRoutineCommentUpdateRequest
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
public class DailyRoutineCommentUpdateRequest {

    @Schema(description = "일련번호", defaultValue = "0")
    @NotNull
    private Long idx = 0L;

    /**일정 일련번호*/
    @Schema(description = "일정 일련번호", defaultValue = "0")
    @NotNull
    private Long dailyIdx = 0L;

    /**내용*/
    @Schema(description = "내용", defaultValue = "")
    @NotEmpty
    private String content = "";

}
