package com.routdoo.dailyroutine.module.place.dto.action.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto.action.reply
 * fileName       : PlaceReplyUpdateRequest
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
public class PlaceReplyUpdateRequest {

    /**일련번호*/
    @Schema(description = "일련번호",defaultValue = "0")
    @NotNull
    private Long idx = 0L;

    /**코멘트 일련번호*/
    @Schema(description = "댓글 일련번호" ,defaultValue = "0")
    @NotNull
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
