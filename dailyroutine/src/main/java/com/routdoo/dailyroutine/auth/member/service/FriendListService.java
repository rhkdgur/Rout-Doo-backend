package com.routdoo.dailyroutine.auth.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.routdoo.dailyroutine.auth.member.domain.FriendList;
import com.routdoo.dailyroutine.auth.member.dto.FriendListDto;
import com.routdoo.dailyroutine.auth.member.repository.FriendListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendListService {

	private final FriendListRepository friendListRepository;
	
	/**
	 * 친구 목록 조회  
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<FriendListDto> selectFriendListResultList(FriendListDto dto) throws Exception {
		List<FriendList> list = friendListRepository.findByBlockYnWithMemberById(dto.getBlockYn(),dto.getMemberId());
		return list.stream().map(x-> new FriendListDto(x)).collect(Collectors.toList());
	}
	
	/***
	 * 친구 목록 등록   
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean insertFriendList(FriendListDto dto) throws Exception {
		 FriendList entity = friendListRepository.save(dto.toEntity());
		 return entity == null ? false : true;
	}
	
	/**
	 * 친구 삭제  
	 * @param dto
	 * @throws Exception
	 */
	public void deleteFriendList(FriendListDto dto) throws Exception {
		friendListRepository.deleteById(dto.getIdx());
	}
}
