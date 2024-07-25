package com.routdoo.dailyroutine.auth.member.dto.action;

import com.routdoo.dailyroutine.common.exception.validate.annotation.date.Date;
import com.routdoo.dailyroutine.common.exception.validate.annotation.phone.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto
 * fileName       : MemberActionRequest
 * author         : rhkdg
 * date           : 2024-05-02
 * description    : 회원정보 등록/수정 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-02        rhkdg       최초 생성
 */
@Schema(description = "회원가입 request Dto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequest implements Serializable {

    @Schema(description = "회원 아이디")
    @NotBlank
    private String id = "";

    @Schema(description = "회원 비밀번호")
    @NotBlank
    private String pw = "";

    @Schema(description = "회원 이메일")
    @Email
    @NotNull
    private String email = "";

    @Schema(description = "회원 닉네임")
    @NotBlank
    private String nickname = "";

    @Schema(description = "회원 전화번호")
    @NotBlank
    @Phone
    private String phonenumber = "";

    @Schema(description = "회원 성별", example = "M(남자),W(여자)")
    @NotBlank
    private String gender = "";

    @Schema(description = "생년월일" , example = "yyyy-MM-dd")
    @NotBlank
    @Date
    private String birth = "";

    @Schema(description = "나이(생년월일로 자동생성)")
    private int age = 0;

    @Schema(description = "MBTI")
    @NotBlank
    private String mbti = "";

    @Schema(description = "자기소개")
    private String introText= "";

    @Schema(description = "회원상태")
    @NotBlank
    private String useStatus = "";

}
