package com.routdoo.dailyroutine.module.place.dto.action.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action.comment
 * fileName       : PlaceCommentDeleteRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 장소 댓글 삭제 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "장소 댓글 삭제 request")
@Getter
@Setter
public class PlaceCommentDeleteRequest {
    @NotBlank
    private long idx =0L;
}
