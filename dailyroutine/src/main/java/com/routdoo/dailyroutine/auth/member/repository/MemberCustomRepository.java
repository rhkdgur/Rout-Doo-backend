package com.routdoo.dailyroutine.auth.member.repository;

import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface MemberCustomRepository {

	/**
	 * 회원 페이징 목록
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<MemberSummaryResponse> selectMemberPageList(MemberDefaultDto searchDto) throws Exception;
	
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
	 * 회원 상태 변경
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	boolean updateMemberUseStatus(MemberDto dto) throws Exception;


	/**
	 * 차단 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<Map<String,Object>> selectMemberFriendsBlockPageList(MemberDefaultDto searchDto) throws Exception;

	/**
	 * 차단 목록( no paging)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> selectMemberFriendsBlockList(MemberDefaultDto searchDto) throws Exception;

	/**
	 *  친구목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<Map<String,Object>> selectMemberFriendsPageList(MemberDefaultDto searchDto) throws Exception;

	/**
	 * 친구목록 paging x
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> selectMemberFriendsList(MemberDefaultDto searchDto) throws Exception;

}
