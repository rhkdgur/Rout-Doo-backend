package com.routdoo.dailyroutine.auth.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto
 * fileName       : MemberSummaryResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : 회원 요약정보
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberSummaryResponse {

    private String id = "";

    private String nickname = "";

    private String gender = "";

    private int age = 0;

    private String introText = "";

    private String mbti = "";

    public MemberSummaryResponse(String id, String nickname, String gender) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
    }

    public MemberSummaryResponse(String id, String nickname, String gender, int age, String introText, String mbti) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.introText = introText;
        this.mbti = mbti;
    }
}
