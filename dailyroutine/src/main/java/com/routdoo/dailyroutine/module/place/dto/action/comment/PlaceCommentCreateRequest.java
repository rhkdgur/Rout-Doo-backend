package com.routdoo.dailyroutine.module.place.dto.action.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceCommentActionRequest
 * author         : GAMJA
 * date           : 2024/05/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCommentCreateRequest {

    @Schema(description = "장소번호" , example = "P20231212001")
    @NotBlank
    /**장소 일련번호*/
    private String placeNum;

    @Schema(description = "내용", example = "라라라라")
    @NotBlank
    /**내용*/
    private String content;

}
