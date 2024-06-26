package com.routdoo.dailyroutine.module.place.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action
 * fileName       : PlaceUpdateRequest
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
public class PlaceUpdateRequest {

    @Schema(description = "장소번호", example = "P20230202001")
    @NotBlank(message = "장소번호는 필수값입니다.")
    /**일련번호*/
    private String placeNum;

    @Schema(description = "제목", example = "장충동왕족발보쌈")
    @NotBlank(message = "제목은 필수값입니다.")
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

    @Schema(description = "상세 주소", example = "부산 해운대구 ...")
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

    @Schema(description = "사용여부", example = "사용 : Y, 미사용 : N")
    @NotBlank
    /**사용여부*/
    private String pstatus;

}
