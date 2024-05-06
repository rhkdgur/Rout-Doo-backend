package com.routdoo.dailyroutine.module.place.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action
 * fileName       : PlaceReplyActionRequest
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 댓글에 대한 답글 등록/수정 ㄱㄷ볃ㄴㅅ
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaceReplyActionRequest {

    /**일련번호*/
    @Schema(description = "일련번호",defaultValue = "0")
    private Long idx = 0L;

    /**코멘트 일련번호*/
    @Schema(description = "댓글 일련번호" ,defaultValue = "0")
    @NotBlank
    private Long commentIdx = 0L;

    /**장소번호*/
    @Schema(description = "장소 번호",defaultValue = "")
    @NotBlank
    private String placeNum = "";

    /**내용*/
    @Schema(description = "내용")
    @NotBlank
    private String content = "";

}
