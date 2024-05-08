package com.routdoo.dailyroutine.auth.member.web;

import com.routdoo.dailyroutine.auth.AuthResultCodeType;
import com.routdoo.dailyroutine.auth.AuthServiceResult;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse;
import com.routdoo.dailyroutine.auth.member.dto.action.MemberCreateRequest;
import com.routdoo.dailyroutine.auth.member.dto.action.MemberUpdateRequest;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseController;
import com.routdoo.dailyroutine.module.place.dto.PlaceIntroDto;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import com.routdoo.dailyroutine.module.routine.service.RoutineRangeConfigType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

	//회원 서비스
	private final MemberService memberService;

	//회원 친구 서비스
	private final FriendListService friendListService;
	
	//일정 서비스
	private final DailyRoutineService dailyRoutineService;
	
	//장소 서비스
	private final PlaceService placeService;
	
	
	/**
	 * 회원 목록 조회 (페이징 포함)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(MGN_URL+"/member/list")
	public Map<String,Object> selectMemberList(MemberDefaultDto searchDto) throws Exception {

		//회원 목록 조회
		Page<MemberSummaryResponse> resultList = memberService.selectMemberPageList(searchDto);
		modelMap.put("resultList", resultList);
		modelMap.put("searchDto",searchDto);
		
		return modelMap;
	}
	
	
	/**
	 * 회원 상세 조회
	 * @return
	 * @throws Exception
	 */
	@GetMapping(MGN_URL+"/member/view")
	public Map<String,Object> selectMemberView(@RequestParam("id") String memberId) throws Exception {



		MemberDto dto = new MemberDto();
		dto.setId(memberId);
		dto = memberService.selectMember(dto);

		MemberDefaultDto searchDto = new MemberDefaultDto();
		searchDto.setMemberId(dto.getId());

		//친구 목록
		List<MemberDto> friendsList = friendListService.selectMemberFriendsList(searchDto);
		
		//차단 목록
		List<MemberDto> blockList = friendListService.selectMypageFriendsBlockList(searchDto);

		//공개일정 개수
		DailyRoutineDefaultDto dailyRoutineDefaultDto = new DailyRoutineDefaultDto();
		dailyRoutineDefaultDto.setRangeType(RoutineRangeConfigType.PUBLIC.name());
		long dailyRoutineCnt = dailyRoutineService.selectDailyRoutineTotalCount(dailyRoutineDefaultDto);

		//장소등록 개수
		PlaceIntroDto placeIntroDto = new PlaceIntroDto();
		placeIntroDto.setMemberId(dto.getId());
		List<PlaceIntroDto> introList = placeService.selectPlaceIntroList(placeIntroDto);

		//회원 상세 정보
		modelMap.put("member", dto);
		//회원 친구 목록
		modelMap.put("friendsList", friendsList);
		//회원 차단 목록
		modelMap.put("blockList", blockList);
		//공개 일정 개수
		modelMap.put("routineCnt", dailyRoutineCnt);
		//장소 등록 개수
		modelMap.put("placeCnt", introList.size());
		
		return modelMap;
	}
	
	
	/**
	 * 회원 등록
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/member/act/ins")
	public ResponseEntity<?> createMember(@RequestBody @Valid MemberCreateRequest memberActionRequest) throws Exception {
		
		try{
			MemberDto checkDto = memberService.selectMember(MemberDto.createOf(memberActionRequest));
			//아이디 존재 여부 확인
			if(checkDto != null) {
				return new ResponseEntity<>("이미 사용중인 아이디 입니다.",HttpStatus.ALREADY_REPORTED);
			}
			
			AuthServiceResult<?> result = memberService.saveMember(MemberDto.createOf(memberActionRequest));
			if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(result.getMessage(),HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
			logger.error("### insert member error ");
			return new ResponseEntity<>("회원 등록시 오류가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>("등록 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 회원 수정
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/member/act/upd")
	public ResponseEntity<?> updateMember(@RequestBody @Valid MemberUpdateRequest memberActionRequest) throws Exception {
		AuthServiceResult<?> result = memberService.saveMember(MemberDto.updateOf(memberActionRequest));
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
			AuthServiceResult<?> result = memberService.deleteMember(dto);
			if(AuthResultCodeType.INFO_FAIL.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>("삭제에 실패하였습니다.",HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
			logger.error("### delete member error");
			return new ResponseEntity<>("삭제시 오류가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
	}

	/**
	 * 회원 삭제 batch
	 * @param memberIds
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/member/act/batch/del")
	public ResponseEntity<?> deleteMemberBatch(@RequestParam("memberIds[]") List<String> memberIds,
											   @RequestParam("useStatus") String useStatus) throws Exception {

		try{
			memberService.updateMemberUseStatusBatch(memberIds,useStatus);
		}catch (Exception e ){
			logger.error("### delete member batch error");
			return new ResponseEntity<>("삭제시 오류가 발생하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>("삭제 되었습니다.", HttpStatus.OK);
	}
}
