package com.routdoo.dailyroutine.auth.member.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.routdoo.dailyroutine.auth.member.MemberResultCodeType;
import com.routdoo.dailyroutine.auth.member.MemberServiceResult;
import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member.web
* @fileName      : MemberUserController.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.14
* @description   : 회원 사용자 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.14        ghgo       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class MemberUserController extends BaseController{
	
	private final MemberService memberService;
	
	/**
	 * 회원 정보 (요약정보)
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/member/view")
	public Map<String,Object> selectMemberView(MemberDto dto) throws Exception {
		
		dto = memberService.selectMember(dto);
		modelMap.put("member", dto);
		
		return modelMap;
	}
	
	/**
	 * 회원 요약 정보
	 */
	@GetMapping("/member/summary/view")
	public Map<String,Object> summaryMemberView(MemberDto dto) throws Exception {
		
		dto = memberService.selectMember(dto);
		modelMap.put("member", dto.getSummaryInfo());
		return modelMap;
	}
	
	/**
	 * 로그인 처리
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/member/login")
	public ResponseEntity<?> loginMember(MemberDto dto,HttpSession session) throws Exception {
		
		MemberServiceResult<MemberDto> result = memberService.loginMember(dto);
		if(!MemberResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
			if(MemberResultCodeType.INFO_ALREADYID.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(result.getMessage(),HttpStatus.NOT_MODIFIED);
			}else {
				return new ResponseEntity<>("로그인시 이슈가 발생하였습니다.",HttpStatus.BAD_REQUEST);
			}
		}
		
		//세션 정보 등록
		MemberSession.createMemberSession(session, (MemberDto)result.getElement());
		return new ResponseEntity<>("로그인 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 로그아웃
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/member/logout")
	public ResponseEntity<?> logoutMember(HttpSession session) throws Exception {
		MemberSession.clearMemberSession(session);
		return new ResponseEntity<>("로그아웃 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 회원 가입
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/member/signup")
	public ResponseEntity<?> createMember(MemberDto dto) throws Exception {
		
		try {
			MemberDto checkDto = memberService.selectMember(dto);
			if(checkDto != null) {
				return new ResponseEntity<>("이미 존재하는 회원 아이디 입니다.",HttpStatus.ALREADY_REPORTED);
			}
			MemberServiceResult<?> result = memberService.saveMember(dto);
			if(!MemberResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>("회원 가입이 진행되지 않았습니다.",HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
			logger.error("### member create error");
			return new ResponseEntity<>("회원 가입시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("가입 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 회원 정보 업데이트
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/member/act/upd")
	public ResponseEntity<?> updateMember(MemberDto dto) throws Exception {
		try {
			MemberServiceResult<MemberDto> result =  memberService.saveMember(dto);
			if(!MemberResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(result.getMessage(),HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
 			logger.error("### update member info error ");
 			return new ResponseEntity<>("회원 정보 업데이트시 이슈가 발생하였습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("수정 되었습니다.",HttpStatus.OK);
	}
	
}
