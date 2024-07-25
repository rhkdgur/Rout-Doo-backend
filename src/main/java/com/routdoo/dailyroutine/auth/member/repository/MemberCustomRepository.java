package com.routdoo.dailyroutine.auth.member.repository;

import com.routdoo.dailyroutine.auth.member.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

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
	 * 회원 정보 수정2
	 * @param memberDto
	 * @return
	 * @throws Exception
	 */
	boolean updateMemberInfo(MemberDto memberDto) throws Exception;

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
	Page<MemberFriendsResponse> selectMemberFriendsBlockPageList(MemberDefaultDto searchDto) throws Exception;

	/**
	 * 차단 목록( no paging)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	List<MemberFriendsResponse> selectMemberFriendsBlockList(MemberDefaultDto searchDto) throws Exception;

	/**
	 *  친구목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<MemberFriendsResponse> selectMemberFriendsPageList(MemberDefaultDto searchDto) throws Exception;

	/**
	 * 친구목록 paging x
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	List<MemberFriendsResponse> selectMemberFriendsList(MemberDefaultDto searchDto) throws Exception;

}
