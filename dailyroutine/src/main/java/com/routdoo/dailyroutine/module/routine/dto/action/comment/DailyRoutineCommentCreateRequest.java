package com.routdoo.dailyroutine.module.routine.dto.action.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineCommentActionRequest
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 일정 댓글 등록/수정
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Schema(description = "일정 댓글 등록/수정 Dto")
@Getter
@Setter
public class DailyRoutineCommentCreateRequest {

    /**일정 일련번호*/
    @Schema(description = "일정 일련번호", defaultValue = "0")
    @NotNull
    private Long dailyIdx = 0L;

    /**내용*/
    @Schema(description = "내용", defaultValue = "")
    @NotEmpty
    private String content = "";

}
