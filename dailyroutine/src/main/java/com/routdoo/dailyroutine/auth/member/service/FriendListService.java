package com.routdoo.dailyroutine.auth.member.service;

import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsResponse;
import com.routdoo.dailyroutine.auth.member.repository.FriendListRepository;
import com.routdoo.dailyroutine.auth.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendListService {

    private final FriendListRepository friendListRepository;

    private final MemberRepository memberRepository;

    /**
     * 친구 목록 조회
     *
     * @param dto
     * @return
     * @throws Exception
     */
    public List<MemberFriendsResponse> selectFriendListResultList(MemberFriendsDto dto) throws Exception {
        List<MemberFriendsResponse> list = friendListRepository.selectMemberBlockYnAndMemberList(dto.getBlockYn(), dto.getMemberId());
        return list;
    }

    /***
     * 친구 목록 등록
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean insertFriendList(MemberFriendsDto dto) throws Exception {
        MemberFriends entity = friendListRepository.save(dto.toEntity());
        return entity == null ? false : true;
    }

    /**
     * 친구 삭제
     *
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void deleteFriendList(MemberFriendsDto dto) throws Exception {
        friendListRepository.deleteById(dto.getIdx());
    }

    /**
     * 친구 차단 여부 업데이트
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updateFriendsBlockYn(MemberFriendsDto dto) throws Exception {
        return memberRepository.updateMemberFriendsBlockYn(dto);
    }

    /**
     * 친구 차단 목록
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<MemberDto> selectMypageFriendsBlockPageList(MemberDefaultDto searchDto) throws Exception {
        return memberRepository.selectMemberFriendsBlockPageList(searchDto);
    }

    /**
     * 친구 차단 목록(no paging)
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<MemberDto> selectMypageFriendsBlockList(MemberDefaultDto searchDto) throws Exception {
        return memberRepository.selectMemberFriendsBlockList(searchDto);
    }

    /**
     * 친구 목록
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<MemberDto> selectMemberFriendsPageList(MemberDefaultDto searchDto) throws Exception {
        return memberRepository.selectMemberFriendsPageList(searchDto);
    }

    /**
     * 친구 목록( no paging)
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<MemberDto> selectMemberFriendsList(MemberDefaultDto searchDto) throws Exception {
        return memberRepository.selectMemberFriendsList(searchDto);
    }
}
