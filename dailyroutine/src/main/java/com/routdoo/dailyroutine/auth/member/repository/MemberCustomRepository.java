package com.routdoo.dailyroutine.auth.member.repository;

import java.util.List;

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

}
