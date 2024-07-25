package com.routdoo.dailyroutine.module.routine.dto.action.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action.reply
 * fileName       : DailyRoutineReplyCommentUpdateRequest
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
public class DailyRoutineReplyCommentUpdateRequest {

    @Schema(description = "일련번호")
    @NotNull
    private Long idx = 0L;

    /**코멘트 일련번호*/
    @Schema(description = "댓글 일련번호")
    @NotNull
    private Long commentIdx = 0L;

    /**내용*/
    @Schema(description = "내용")
    @NotEmpty
    private String content = "";

}
