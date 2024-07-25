package com.routdoo.dailyroutine.auth.member.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto.action
 * fileName       : MemberUpdateRequest
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
@NoArgsConstructor
public class MemberUpdateSummaryRequest {

    @Schema(description = "회원 닉네임")
    @NotBlank
    private String nickname = "";

    @Schema(description = "MBTI")
    @NotBlank
    private String mbti = "";

    @Schema(description = "자기소개")
    private String introText= "";

}
