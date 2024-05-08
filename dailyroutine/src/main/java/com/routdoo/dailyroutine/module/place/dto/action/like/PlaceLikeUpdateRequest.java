package com.routdoo.dailyroutine.module.place.dto.action.like;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action.like
 * fileName       : PlaceLikeUpdateRequest
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
public class PlaceLikeUpdateRequest {
    @Schema(description = "장소 좋아요 일련번호", defaultValue = "0" , example = "1")
    @NotNull(message = "장소 좋아요 일련번호는 필수값입니다.")
    private Long idx = 0L;

    @Schema(description = "장소번호", example = "P20230202001")
    @NotBlank
    private String placeNum;
}
