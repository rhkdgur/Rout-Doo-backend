package com.routdoo.dailyroutine.module.place.dto.action.record;

import com.routdoo.dailyroutine.common.exception.validate.annotation.phone.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action
 * fileName       : PlaceRecordActionRequest
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 장소 정보 수정 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Schema(description = "장소 정보 수정 제안 request")
@Getter
@Setter
public class PlaceRecordActionRequest {

    @Schema(description = "장소 정보 수정 일련번호", defaultValue = "0", example = "0")
    private Long idx = 0L;

    /**일련번호*/
    @Schema(description = "장소번호", defaultValue = "", example = "P2020200001")
    @NotBlank
    private String placeNum;

    /**연락처*/
    @Schema(description = "연락처", defaultValue = "", example = "010-0000-0000")
    @NotEmpty
    @Phone
    private String tel;

    /**주소*/
    @Schema(description = "주소", defaultValue = "", example = "부산 해운대구...")
    @NotBlank
    private String addr;

    /**경도*/
    @Schema(description = "경도", defaultValue = "", example = "31.231231")
    @NotBlank
    private String mapx;

    /**위도*/
    @Schema(description = "위도", defaultValue = "", example = "127.123124")
    @NotBlank
    private String mapy;

    /**이용안내*/
    @Schema(description = "이용안내", defaultValue = "", example = "이용안내")
    @NotEmpty
    private String useInfo;

    /**상세내용*/
    @Schema(description = "상세 내용", defaultValue = "", example = "상세내용")
    @NotEmpty
    private String detailText;

    /**사용여부*/
    @Schema(description = "사용여부", defaultValue = "", example = "Y, N")
    @NotBlank
    private String useType;

}
