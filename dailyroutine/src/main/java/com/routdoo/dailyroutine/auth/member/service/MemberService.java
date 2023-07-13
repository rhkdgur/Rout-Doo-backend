package com.routdoo.dailyroutine.auth.member.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routdoo.dailyroutine.auth.member.MemberResultCodeType;
import com.routdoo.dailyroutine.auth.member.MemberServiceResult;
import com.routdoo.dailyroutine.auth.member.domain.Member;
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
	
	
	/**
	 * 회원 등록,수정 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public MemberServiceResult<MemberDto> saveMember(MemberDto dto) throws Exception {
		Member member = memberRepository.findById(dto.getId()).orElse(null);
		if(member == null) {
			member = memberRepository.save(dto.toEntity());
			if(member != null) {
				return new MemberServiceResult<>(MemberResultCodeType.INFO_OK,new MemberDto(member));
			}else {
				return new MemberServiceResult<>(MemberResultCodeType.INFO_FAIL,"등록에 실패하였습니다.");
			}
		}else {
			member.changeMember(dto);
			return new MemberServiceResult<>(MemberResultCodeType.INFO_OK,"업데이트 되었습니다.",new MemberDto(member));
		}
	}
	
	/**
	 * 회원 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public MemberServiceResult<?> deleteMember(MemberDto dto) throws Exception {
		memberRepository.deleteById(dto.getId());
		Member member = memberRepository.findById(dto.getId()).orElse(null);
		if(member == null) {
			return new MemberServiceResult<>(MemberResultCodeType.INFO_OK,"삭제 되었습니다.");
		}else {
			return new MemberServiceResult<>(MemberResultCodeType.INFO_FAIL,"삭제 되지않았습니다.");
		}
	}

}
