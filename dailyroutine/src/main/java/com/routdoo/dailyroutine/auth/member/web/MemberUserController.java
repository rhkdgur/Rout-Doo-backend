package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.AuthResultCodeType;
import com.routdoo.dailyroutine.auth.AuthServiceResult;
import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

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
@Tag(name="회원 사용자 컨트롤")
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class MemberUserController extends BaseModuleController{
	
	private final MemberService memberService;
	
	private final MemberSession memberSession;


	
	/**
	 * 회원 정보 (요약정보)
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 상세 정보")
	@GetMapping(API_URL+"/member/view")
	public Map<String,Object> selectMemberView() throws Exception {
		
		modelMap = new LinkedHashMap<>();
		
		MemberDto dto = memberSession.getMemberSession();
		dto = memberService.selectMember(dto);
		modelMap.put("member", dto);
		
		return modelMap;
	}
	
	/**
	 * 회원 요약 정보
	 */
	@Operation(summary="사용자 상세 정보(요약정보)")
	@GetMapping(API_URL+"/member/summary/view")
	public Map<String,Object> summaryMemberView() throws Exception {
		
		modelMap = new LinkedHashMap<>();
		
		MemberDto dto = memberSession.getMemberSession();
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
	@Operation(summary="사용자 로그")
	@Parameters(value={
		@Parameter(name = "id", description ="아이디 "),
		@Parameter(name = "pw", description="비밀번호 ")
	})
	@PostMapping(API_URL+"/member/login")
	public ResponseEntity<?> loginMember(@RequestParam("id") String id,
										 @RequestParam("pw") String pw) throws Exception {
		
		MemberDto dto = new MemberDto();
		dto.setId(id);
		dto.setPw(pw);
		AuthServiceResult<MemberDto> result = memberService.loginMember(dto);
		if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
			if(AuthResultCodeType.INFO_NOMATCH.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(result.getMessage(),HttpStatus.NOT_MODIFIED);
			}else {
				return new ResponseEntity<>("로그인시 이슈가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}
		
		//세션 정보 등록
		String token = memberSession.createMemberSession((MemberDto)result.getElement());
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	
	/**
	 * 로그아웃
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 로그아웃 ")
	@PostMapping(API_URL+"/member/logout")
	public ResponseEntity<?> logoutMember() throws Exception {
		memberSession.clearMemberSession();
		return new ResponseEntity<>("로그아웃 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 회원 가입
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 회원가입 ")
	@Parameters({
		@Parameter(name = "id", description ="아이디 "),
		@Parameter(name = "pw", description ="비밀번호 "),
		@Parameter(name = "email", description ="이메일"),
		@Parameter(name = "nickname", description ="닉네임"),
		@Parameter(name = "phonenumber", description ="휴대전화번호"),
		@Parameter(name = "gender", description ="성별"),
		@Parameter(name = "age", description ="나이"),
		@Parameter(name = "birth", description ="생년월일"),
		@Parameter(name = "mbti", description ="MBTI")
	})
	@PostMapping(API_URL+"/member/signup")
	public ResponseEntity<?> createMember(@RequestBody MemberDto dto) throws Exception {
		
		try {
			MemberDto checkDto = memberService.selectMember(dto);
			if(checkDto != null) {
				return new ResponseEntity<>("이미 존재하는 회원 아이디 입니다.",HttpStatus.ALREADY_REPORTED);
			}
			AuthServiceResult<?> result = memberService.saveMember(dto);
			if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>("회원 가입이 진행되지 않았습니다.",HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
			logger.error("### member create error {}",e.getMessage());
			return new ResponseEntity<>("회원 가입시 오류가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>("가입 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 회원 정보 업데이트
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 회원 정보 업데이트 ")
	@Parameters(value={
		@Parameter(name = "id", description="아이디 "),
		@Parameter(name = "pw", description="비밀번호 "),
		@Parameter(name = "email", description="이메일"),
		@Parameter(name = "nickname", description="닉네임"),
		@Parameter(name = "phonenumber", description="휴대전화번호"),
		@Parameter(name = "gender", description="성별"),
		@Parameter(name = "age", description="나이"),
		@Parameter(name = "birth", description="생년월일"),
		@Parameter(name = "mbti", description = "MBTI")
	})
	@PostMapping(API_URL+"/member/act/upd")
	public ResponseEntity<?> updateMember(@RequestBody MemberDto dto) throws Exception {
		try {
			AuthServiceResult<MemberDto> result =  memberService.saveMember(dto);
			if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(result.getMessage(),HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
 			logger.error("### update member info error ");
 			return new ResponseEntity<>("회원 정보 업데이트시 이슈가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>("수정 되었습니다.",HttpStatus.OK);
	}

}
