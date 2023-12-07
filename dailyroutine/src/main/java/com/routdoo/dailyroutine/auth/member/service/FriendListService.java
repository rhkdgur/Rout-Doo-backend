package com.routdoo.dailyroutine.auth.member.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.repository.FriendListRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendListService {

	private final FriendListRepository friendListRepository;

	private final MemberRepository memberRepository;
	
	/**
	 * 친구 목록 조회  
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<MemberFriendsDto> selectFriendListResultList(MemberFriendsDto dto) throws Exception {
		List<MemberFriends> list = friendListRepository.findByBlockYnAndMember_Id(dto.getBlockYn(),dto.getMemberId());
		return list.stream().map(x-> new MemberFriendsDto(x)).collect(Collectors.toList());
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
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void deleteFriendList(MemberFriendsDto dto) throws Exception {
		friendListRepository.deleteById(dto.getIdx());
	}

	/**
	 * 친구 차단 여부 업데이트
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean updateFriendsBlockYn(MemberFriendsDto dto) throws Exception {
		return memberRepository.updateMemberFriendsBlockYn(dto);
	}

	/**
	 * 친구 차단 처리 헤제 여부
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<Map<String,Object>> selectMypageFriendsBlockPageList(MemberDefaultDto searchDto) throws Exception {
		return memberRepository.selectMemberFriendsBlockPageList(searchDto);
	}

	/**
	 * 친구 목록
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<Map<String,Object>> selectMemberFriendsPageList(MemberDefaultDto searchDto) throws Exception {
		return memberRepository.selectMemberFriendsPageList(searchDto);
	}
}
