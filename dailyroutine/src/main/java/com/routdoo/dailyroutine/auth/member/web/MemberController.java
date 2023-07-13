package com.routdoo.dailyroutine.auth.member.web;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.routdoo.dailyroutine.auth.member.MemberResultCodeType;
import com.routdoo.dailyroutine.auth.member.MemberServiceResult;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseController;

import lombok.RequiredArgsConstructor;

/**
* @packageName   : com.routdoo.dailyroutine.auth.member.web
* @fileName      : MemberController.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.13
* @description   : 관리자 회원 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.13        ghgo       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class MemberController extends BaseController{

	private final MemberService memberService;
	
	
	/**
	 * 회원 목록 조회 (페이징 포함)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(MGN_URL+"/member/list")
	public Map<String,Object> selectMemberList(MemberDefaultDto searchDto) throws Exception {

		//회원 목록 조회
		Page<MemberDto> resultList = memberService.selectMemberPageList(searchDto);
		modelMap.put("memberList", resultList);
		
		return modelMap;
	}
	
	
	/**
	 * 회원 상세 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(MGN_URL+"/member/view")
	public Map<String,Object> selectMemberView(MemberDto dto) throws Exception {
		
		dto = memberService.selectMember(dto);
		modelMap.put("member", dto);
		
		return modelMap;
	}
	
	
	/**
	 * 회원 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/member/act/ins")
	public ResponseEntity<?> createMember(MemberDto dto,SessionStatus status) throws Exception {
		
		try{
			MemberDto checkDto = memberService.selectMember(dto);
			//아이디 존재 여부 확인
			if(checkDto != null) {
				return new ResponseEntity<>("이미 사용중인 아이디 입니다.",HttpStatus.ALREADY_REPORTED);
			}
			
			MemberServiceResult<?> result = memberService.saveMember(dto);
			if(!MemberResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(result.getMessage(),HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
			logger.error("### insert member error ");
			return new ResponseEntity<>("회원 등록시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		status.setComplete();
		return new ResponseEntity<>("등록 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 회원 수정
	 * @param dto
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/member/act/upd")
	public ResponseEntity<?> updateMember(MemberDto dto, SessionStatus status) throws Exception {
		
		MemberServiceResult<?> result = memberService.saveMember(dto);
		
		status.setComplete();
		return new ResponseEntity<>("수정 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 회원 삭제
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/member/act/del")
	public ResponseEntity<?> deleteMember(@RequestParam("id") String id) throws Exception {
		
		MemberDto dto = new MemberDto();
		dto.setId(id);
		dto = memberService.selectMember(dto);
		
		try {
			MemberServiceResult<?> result = memberService.deleteMember(dto);
			if(MemberResultCodeType.INFO_FAIL.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>("삭제에 실패하였습니다.",HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
			logger.error("### delete member error");
			return new ResponseEntity<>("삭제시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
	}
}
