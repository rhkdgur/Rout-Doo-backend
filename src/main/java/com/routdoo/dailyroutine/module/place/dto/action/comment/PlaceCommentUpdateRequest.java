package com.routdoo.dailyroutine.module.place.dto.action.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action.comment
 * fileName       : PlaceCommentUpdateRequest
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
public class PlaceCommentUpdateRequest {
    @Schema(description = "댓글 일련번호", example = "1")
    @NotNull
    /**일련번호*/
    private Long idx = 0L;

    @Schema(description = "장소번호" , example = "P20231212001")
    @NotBlank
    /**장소 일련번호*/
    private String placeNum;

    @Schema(description = "내용", example = "라라라라")
    @NotBlank
    /**내용*/
    private String content;
}
