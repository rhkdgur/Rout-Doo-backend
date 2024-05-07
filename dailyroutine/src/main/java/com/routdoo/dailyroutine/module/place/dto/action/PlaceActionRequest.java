package com.routdoo.dailyroutine.module.place.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class PlaceActionRequest {

    @Schema(description = "장소번호", example = "P20230202001")
    /**일련번호*/
    private String placeNum;

    @Schema(description = "제목", example = "장충동왕족발보쌈")
    @NotBlank
    /**제목*/
    private String title;

    @Schema(description = "연락처", example = "051-000-0000")
    @NotNull
    /**연락처*/
    private String tel;

    @Schema(description = "카테고리 코드", example = "TEST")
    @NotBlank
    /**카테고리 코드*/
    private String categCd;

    @Schema(description = "주소", example = "부산 해운대구 ...")
    @NotEmpty
    /**주소*/
    private String addr;

    @Schema(description = "경도", example = "31.2321231")
    @NotEmpty
    /**경도*/
    private String mapx;

    @Schema(description = "위도", example = "127.1241231")
    @NotEmpty
    /**위도*/
    private String mapy;

    @Schema(description = "이용안내", example = "이용안내")
    @NotEmpty
    /**이용안내*/
    private String useInfo;

    @Schema(description = "상세정보", example = "상세정보")
    @NotEmpty
    /**상세정보*/
    private String detailText;

    @Schema(description = "사용여부", example = "사용 : Y, 미사용 : N")
    @NotBlank
    /**사용여부*/
    private String pstatus;

}
