package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.AuthResultCodeType;
import com.routdoo.dailyroutine.auth.AuthServiceResult;
import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@CrossOrigin("*")
@RestController
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
	 * @param id
	 * @param pw
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 로그인")
	@Parameters(value={
		@Parameter(name = "id", description ="아이디 "),
		@Parameter(name = "pw", description="비밀번호 ")
	})
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "token 전달"),
			@ApiResponse(responseCode = "304", description = "아이디 또는 비밀번호가 일치하지 않는 상태"),
			@ApiResponse(responseCode = "422", description = "로그인 접근시 이슈 발생")
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
	@ApiResponse(responseCode = "200", description = "로그아웃")
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
	@Operation(summary="사용자 회원가입 ", description = "요청하는 파라미터는 모두 입력할 수 있도록 할 것")
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
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "가입 완료"),
			@ApiResponse(responseCode = "208", description = "이미 존재하는 회원 아이디"),
			@ApiResponse(responseCode = "422", description = "회원 가입이 진행되지 않음"),
			@ApiResponse(responseCode = "400", description = "회원 가입시 오류가 발생")
	})
	@PostMapping(API_URL+"/member/signup")
	public ResponseEntity<?> createMember(MemberDto dto) throws Exception {
		
		try {
			MemberDto checkDto = memberService.selectMember(dto);
			if(checkDto != null) {
				return new ResponseEntity<>("이미 존재하는 회원 아이디 입니다.",HttpStatus.ALREADY_REPORTED);
			}
			AuthServiceResult<?> result = memberService.saveMember(dto);
			if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>("회원 가입이 진행되지 않았습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("### member create error {}",e.getMessage());
			return new ResponseEntity<>("회원 가입시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("가입 되었습니다.",HttpStatus.OK);
	}

	/**
	 * 중복아이디 체크
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "중복아이디 체크",description = "id를 이용하여 중복체크합니다.")
	@Parameter(name="id", description = "아이디")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "208", description = "중복되는 아이디"),
					@ApiResponse(responseCode = "422", description = "에러발생"),
					@ApiResponse(responseCode = "200", description = "사용가능한 아이디")
			}
	)
	@PostMapping(API_URL+"/member/signup/idcheck")
	public ResponseEntity<String>  createMemberIdCheck(@RequestParam("id") String id) throws Exception {

		try{
				MemberDto memberDto = new MemberDto();
				memberDto.setId(id);
				memberDto = memberService.selectMember(memberDto);
				if(memberDto != null ){
					return new ResponseEntity<>("이미 존재하는 회원 아이디 입니다.", HttpStatus.ALREADY_REPORTED);
				}
		}catch (Exception e) {
			logger.error("### member id check error : {}",e.getMessage());
			return new ResponseEntity<>("중복아이디 체크시 에러가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>("사용가능한 아이디입니다.",HttpStatus.OK);
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
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "수정 완료"),
			@ApiResponse(responseCode = "304", description = "회원정보 업데이트에 실패"),
			@ApiResponse(responseCode = "422", description = "회원정보 업데이트시 오류")
	})
	@PostMapping(API_URL+"/member/act/upd")
	public ResponseEntity<?> updateMember(MemberDto dto) throws Exception {
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


	/**
	 * 회원 조회
	 * @param sstring
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "회원 목록 조회(친구리스트 닉네임 조회)")
	@Parameter(name="sstring", description="검색어")
	@GetMapping(API_URL+"/member/nickname/list")
	public Map<String,Object> selectMemberList(@RequestParam("sstring") String sstring) throws Exception {

		modelMap = new LinkedHashMap<>();

		MemberDefaultDto searchDto = new MemberDefaultDto();
		searchDto.setStype("nickname");
		searchDto.setSstring(sstring);
		searchDto.setSize(20);

		Page<MemberDto> resultList = memberService.selectMemberPageList(searchDto);
		modelMap.put("memberList",resultList);

		return modelMap;
	}

}
