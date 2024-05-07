package com.routdoo.dailyroutine.auth.member.dto.action.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto.action.friend
 * fileName       : MemberFriendsCreateRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 친구 등록 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "친구 등록 request")
@Getter
@Setter
public class MemberFriendsCreateRequest {
    /**회원아이디*/
    @Schema(description = "회원아이디")
    @NotBlank
    private String memberId = "";

    /**차단여부 */
    @Schema(description = "차단여부", example = "Y(차단),N(차단안함))")
    @NotEmpty
    private String blockYn = "";
}
