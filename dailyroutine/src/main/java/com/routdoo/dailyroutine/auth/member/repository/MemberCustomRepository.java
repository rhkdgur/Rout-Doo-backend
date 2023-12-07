package com.routdoo.dailyroutine.auth.member.repository;

import java.util.List;
import java.util.Map;

import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import org.springframework.data.domain.Page;

import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;

public interface MemberCustomRepository {

	/**
	 * 회원 페이징 목록
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<MemberDto> selectMemberPageList(MemberDefaultDto searchDto) throws Exception;
	
	/**
	 * 회원 리스트
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	List<MemberDto> selectMemberList(MemberDefaultDto searchDto) throws Exception;
	
	/**
	 * 회원 상세
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	MemberDto selectMember(MemberDto dto) throws Exception;


	/**
	 * 차단 여부 업데이트
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	boolean updateMemberFriendsBlockYn(MemberFriendsDto dto) throws Exception;


	/**
	 * 차단 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<Map<String,Object>> selectMemberFriendsBlockPageList(MemberDefaultDto searchDto) throws Exception;

	/**
	 *  친구목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<Map<String,Object>> selectMemberFriendsPageList(MemberDefaultDto searchDto) throws Exception;

}
