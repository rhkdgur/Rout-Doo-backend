package com.routdoo.dailyroutine.auth.member.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.routdoo.dailyroutine.auth.member.MemberResultCodeType;
import com.routdoo.dailyroutine.auth.member.MemberServiceResult;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseController;

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
	 * 회원 정보
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
}
