package com.routdoo.dailyroutine.auth.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto
 * fileName       : MemberListAndFriendListResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    : 회원 목록과 친구 목록 response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
public class MemberListAndFriendListResponse {

    private Page<MemberSummaryResponse> memberList;

    private List<MemberFriendsResponse> friendList;

    public MemberListAndFriendListResponse(Page<MemberSummaryResponse> memberList, List<MemberFriendsResponse> friendList) {
        this.memberList = memberList;
        this.friendList = friendList;
    }
}
