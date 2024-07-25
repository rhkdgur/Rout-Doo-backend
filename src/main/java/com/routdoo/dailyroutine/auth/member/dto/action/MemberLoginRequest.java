package com.routdoo.dailyroutine.auth.member.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto.action
 * fileName       : MemberLoginRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 로그인 처리 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "로그인 처리 request")
@Getter
@Setter
public class MemberLoginRequest {
    
    @Schema(description = "아이디")
    @NotBlank(message = "아이디는 필수값입니다.")
    private String id = "";

    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String pw = "";
}
