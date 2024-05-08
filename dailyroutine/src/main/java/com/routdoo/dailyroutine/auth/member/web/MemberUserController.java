package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.AuthResultCodeType;
import com.routdoo.dailyroutine.auth.AuthServiceResult;
import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse;
import com.routdoo.dailyroutine.auth.member.dto.action.MemberCheckIdRequest;
import com.routdoo.dailyroutine.auth.member.dto.action.MemberCreateRequest;
import com.routdoo.dailyroutine.auth.member.dto.action.MemberLoginRequest;
import com.routdoo.dailyroutine.auth.member.dto.action.MemberUpdateRequest;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.vo.CommonResponse;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public MemberDto selectMemberView() throws Exception {

		MemberDto memberDto = memberSession.getMemberSession();
		memberDto = memberService.selectMember(memberDto);
		
		return MemberDto.of(memberDto);
	}
	
	/**
	 * 회원 요약 정보
	 */
	@Operation(summary="사용자 상세 정보(요약정보)")
	@GetMapping(API_URL+"/member/summary/view")
	public MemberSummaryResponse summaryMemberView() throws Exception {

		MemberDto dto = memberSession.getMemberSession();
		dto = memberService.selectMember(dto);
		return MemberSummaryResponse.dtoResponseOf(dto);
		
	}
	
	/**
	 * 로그인 처리
	 * @param memberLoginRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 로그인")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "token 전달"),
			@ApiResponse(responseCode = "304", description = "아이디 또는 비밀번호가 일치하지 않는 상태"),
			@ApiResponse(responseCode = "422", description = "로그인 접근시 이슈 발생")
	})
	@PostMapping(API_URL+"/member/login")
	public ResponseEntity<?> loginMember(final @Valid @RequestBody MemberLoginRequest memberLoginRequest) throws Exception {
		
		MemberDto dto = new MemberDto();
		dto.setId(memberLoginRequest.getId());
		dto.setPw(memberLoginRequest.getPw());
		AuthServiceResult<MemberDto> result = memberService.loginMember(dto);
		if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
			if(AuthResultCodeType.INFO_NOMATCH.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.NOT_MODIFIED);
			}else {

				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("로그인시 이슈가 발생하였습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}
		
		//세션 정보 등록
		String token = memberSession.createMemberSession((MemberDto)result.getElement());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + token);

		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("로그인에 성공하였습니다."),httpHeaders,HttpStatus.OK);
	}
	
	/**
	 * 로그아웃
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 로그아웃 ")
	@ApiResponse(responseCode = "200", description = "로그아웃")
	@PostMapping(API_URL+"/member/logout")
	public ResponseEntity<?> logoutMember() throws Exception {
		memberSession.clearMemberSession();
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("로그아웃 되었습니다."),HttpStatus.OK);
	}
	
	/**
	 * 회원 가입
	 * @param memberCreateRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 회원가입 ", description = "요청하는 파라미터는 모두 입력할 수 있도록 할 것")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "가입 완료"),
			@ApiResponse(responseCode = "208", description = "이미 존재하는 회원 아이디"),
			@ApiResponse(responseCode = "422", description = "회원 가입이 진행되지 않음"),
			@ApiResponse(responseCode = "400", description = "회원 가입시 오류가 발생")
	})
	@PostMapping(API_URL+"/member/signup")
	public ResponseEntity<?> createMember(final @Valid @RequestBody MemberCreateRequest memberCreateRequest) throws Exception {

		try {
			MemberDto checkDto = memberService.selectMember(MemberDto.createOf(memberCreateRequest));
			if(checkDto != null) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("이미 존재하는 회원 아이디 입니다."),HttpStatus.ALREADY_REPORTED);
			}
			AuthServiceResult<?> result = memberService.saveMember(MemberDto.createOf(memberCreateRequest));
			if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("회원 가입이 진행되지 않았습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("### member create error {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("회원 가입시 오류가 발생했습니다."),HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("가입 되었습니다."),HttpStatus.OK);
	}

	/**
	 * 중복아이디 체크
	 * @param memberCheckIdRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "중복아이디 체크",description = "id를 이용하여 중복체크합니다.")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "208", description = "중복되는 아이디"),
					@ApiResponse(responseCode = "422", description = "에러발생"),
					@ApiResponse(responseCode = "200", description = "사용가능한 아이디")
			}
	)
	@PostMapping(API_URL+"/member/signup/idcheck")
	public ResponseEntity<?>  createMemberIdCheck(final @Valid @RequestBody MemberCheckIdRequest memberCheckIdRequest) throws Exception {

		try{
				MemberDto memberDto = new MemberDto();
				memberDto.setId(memberCheckIdRequest.getId());
				memberDto = memberService.selectMember(memberDto);
				if(memberDto != null ){
					return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("이미 존재하는 회원 아이디 입니다."), HttpStatus.ALREADY_REPORTED);
				}
		}catch (Exception e) {
			logger.error("### member id check error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("중복아이디 체크시 에러가 발생하였습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("사용가능한 아이디입니다."),HttpStatus.OK);
	}
	
	/**
	 * 회원 정보 업데이트
	 * @param memberUpdateRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 회원 정보 업데이트 ")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "수정 완료"),
			@ApiResponse(responseCode = "304", description = "회원정보 업데이트에 실패"),
			@ApiResponse(responseCode = "422", description = "회원정보 업데이트시 오류")
	})
	@PutMapping(API_URL+"/member/act/upd")
	public ResponseEntity<?> updateMember(final @Valid @RequestBody MemberUpdateRequest memberUpdateRequest) throws Exception {
		try {
			AuthServiceResult<MemberDto> result =  memberService.saveMember(MemberDto.updateOf(memberUpdateRequest));
			if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
 			logger.error("### update member info error ");
 			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("회원 정보 업데이트시 이슈가 발생하였습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("수정 되었습니다."),HttpStatus.OK);
	}


	/**
	 * 회원 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "회원 목록 조회(친구리스트 닉네임 조회)")
	@Parameter(name="sstring", description="검색어" ,required = false)
	@GetMapping(API_URL+"/member/nickname/list")
	public Page<MemberSummaryResponse> selectMemberList(@Parameter(hidden = true) MemberDefaultDto searchDto) throws Exception {
		searchDto.setStype("nickname");
		searchDto.setSize(20);
		return  memberService.selectMemberPageList(searchDto);
	}

}
