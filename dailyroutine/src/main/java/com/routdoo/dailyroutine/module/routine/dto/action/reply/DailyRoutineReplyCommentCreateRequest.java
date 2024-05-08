package com.routdoo.dailyroutine.module.routine.dto.action.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineReplyCommentActionRequest
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 일정 댓글에 대한 답글 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Schema(description = "일정 답글 response dto")
@Getter
@Setter
public class DailyRoutineReplyCommentCreateRequest {

    /**회원 아이디*/
    @Schema(description = "회원 아이디")
    private String memberId = "";

    /**코멘트 일련번호*/
    @Schema(description = "댓글 일련번호")
    @NotBlank
    private Long commentIdx = 0L;

    /**내용*/
    @Schema(description = "내용")
    @NotEmpty
    private String content = "";

}
