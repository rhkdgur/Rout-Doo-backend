package com.routdoo.dailyroutine.module.routine.dto.action.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action.reply
 * fileName       : DailyRoutineReplyCommentDeleteRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 댓글에 대한 답글 삭제 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "댓글에 대한 답글 삭제 request")
@Getter
@Setter
public class DailyRoutineReplyCommentDeleteRequest {
    @Schema(description = "일련번호")
    @NotNull
    private Long idx = 0L;
}
