package com.routdoo.dailyroutine.module.place.dto.action.intro;

import com.routdoo.dailyroutine.common.exception.validate.annotation.date.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action.intro
 * fileName       : PlaceIntroCreateRequest
 * author         : rhkdg
 * date           : 2024-06-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceIntroUpdateRequest {

    @Schema(description = "소개글 일련번호")
    @NotNull
    private long idx;

    /**장소번호*/
    @Schema(description = "장소 일련번호", defaultValue = "", example = "P202000001")
    @NotBlank
    private String placeNum;

    /**소개글*/
    @Schema(description = "소개글", defaultValue = "", example = "안녕하세요...")
    @NotBlank
    private String introText;

    /**방문일자*/
    @Schema(description = "방문일자", defaultValue = "", example = "2020-00-00")
    @Date
    @NotBlank
    private String visitDate;

    /**별점*/
    @Schema(description = "별점", defaultValue = "0", example = "4")
    @NotNull
    private int score;

}
