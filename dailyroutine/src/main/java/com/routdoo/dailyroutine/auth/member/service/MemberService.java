package com.routdoo.dailyroutine.auth.member.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.user.service
* @fileName      : MemberService.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.12
* @description   : 회원 service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.12        ghgo       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	/**회원 리포지토리*/
	private final MemberRepository memberRepository;
	
	
	/**
	 * 회원 리스트(no paging)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<MemberDto> selectMemberList(MemberDefaultDto searchDto) throws Exception {
		return memberRepository.selectMemberList(searchDto);
	}
	
	/**
	 * 회원 상세
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public MemberDto selectMember(MemberDto dto) throws Exception {
		return memberRepository.selectMember(dto);
	}
	
	
	/**
	 * 회원 리스트(paging)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<MemberDto> selectMemberPageList(MemberDefaultDto searchDto) throws Exception {
		return memberRepository.selectMemberPageList(searchDto);
	}

}
