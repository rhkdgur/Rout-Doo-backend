package com.routdoo.dailyroutine.module.place.dto.action.record;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action.record
 * fileName       : PlaceRecordDeleteRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 장소 정보 삭제 요청에 대한 글 삭제 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "장소 정보 삭제 요청에 대한 글 삭제 request ")
@Getter
@Setter
public class PlaceRecordDeleteRequest {
    @Schema(description = "장소 정보 삭제 요청 idx")
    @NotBlank
    private long idx = 0L;
}
