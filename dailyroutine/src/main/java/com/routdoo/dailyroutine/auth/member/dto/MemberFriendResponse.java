package com.routdoo.dailyroutine.auth.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto
 * fileName       : MemberSummaryResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : 친구 회원 요약정보
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberFriendResponse {

    private String inviteId = "";

    private String nickname = "";

    private String gender = "";

    private int age = 0;

    private String introText = "";

    private String mbti = "";

    private String blockYn = "";

}
