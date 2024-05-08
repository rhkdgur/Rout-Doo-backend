package com.routdoo.dailyroutine.module.place.dto.action.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action.reply
 * fileName       : PlaceReplyDeleteRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 장소 댓글에 대한 답글 삭제 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "장소 댓글에 대한 답글 삭제 request ")
@Getter
@Setter
public class PlaceReplyDeleteRequest {
    @NotNull(message = "답글 일련번호는 필수값입니다.")
    private long idx = 0L;
}
