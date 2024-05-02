package com.routdoo.dailyroutine.auth.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto
 * fileName       : MemberFriendsResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
public class MemberFriendsResponse implements Serializable {

    private long idx = 0L;

    private long dailyIdx = 0L;

    private String blockYn = "";

    private MemberSummaryResponse memberSummaryResponse = new MemberSummaryResponse();

    public MemberFriendsResponse(long idx, long dailyIdx, String blockYn,
                                 String nickname, String gender, int age, String mbti) {
        this.idx = idx;
        this.dailyIdx = dailyIdx;
        this.blockYn = blockYn;
        this.memberSummaryResponse.setNickname(nickname);
        this.memberSummaryResponse.setGender(gender);
        this.memberSummaryResponse.setAge(age);
        this.memberSummaryResponse.setMbti(mbti);
    }
}
