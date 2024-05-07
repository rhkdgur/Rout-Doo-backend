package com.routdoo.dailyroutine.module.place.dto.action.like;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action
 * fileName       : PlaceLikeActionRequest
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 장소 좋아요 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Schema(description = "장소 좋아요 action request")
@Getter
@Setter
public class PlaceLikeActionRequest {

    @Schema(description = "장소 좋아요 일련번호", defaultValue = "0" , example = "1")
    private Long idx = 0L;

    @Schema(description = "장소번호", example = "P20230202001")
    @NotBlank
    private String placeNum;

}
