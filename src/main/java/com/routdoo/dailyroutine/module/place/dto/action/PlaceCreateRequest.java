package com.routdoo.dailyroutine.module.place.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action
 * fileName       : PlaceActionRequest
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
public class PlaceCreateRequest implements Serializable {

    @Schema(description = "제목", example = "장충동왕족발보쌈")
    @NotBlank
    /**제목*/
    private String title;

    @Schema(description = "카테고리 코드", example = "TEST")
    @NotBlank
    /**카테고리 코드*/
    private String categCd;

    @Schema(description = "주소", example = "부산 해운대구 ...")
    @NotEmpty
    /**주소*/
    private String addr;

    @Schema(description = "상세 주소", example = "100동 301호..")
    @NotEmpty
    /**주소*/
    private String addrDetail;

    @Schema(description = "경도", example = "31.2321231")
    @NotEmpty
    /**경도*/
    private String mapx;

    @Schema(description = "위도", example = "127.1241231")
    @NotEmpty
    /**위도*/
    private String mapy;

}
