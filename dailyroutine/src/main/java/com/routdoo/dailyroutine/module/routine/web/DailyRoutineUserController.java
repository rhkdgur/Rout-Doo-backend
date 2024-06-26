package com.routdoo.dailyroutine.module.routine.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.*;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.vo.CommonResponse;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.routine.RoutineResultCodeType;
import com.routdoo.dailyroutine.module.routine.RoutineServiceResult;
import com.routdoo.dailyroutine.module.routine.dto.*;
import com.routdoo.dailyroutine.module.routine.dto.action.DailyRoutineChangeRangeTypeRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.DailyRoutineCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.DailyRoutineDeleteRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.DailyRoutineUpdateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.invite.DailyRoutineInviteCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.invite.DailyRoutineInviteDeleteRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.timeline.DailyRoutineTimeLineCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.timeline.DailyRoutineTimeLineDeleteRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.timeline.DailyRoutineTimeLineUpdateRequest;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import com.routdoo.dailyroutine.module.routine.service.RoutineRangeConfigType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.web
* @fileName      : DailyRoutinUserController.java
* @author        : Gwang hyeok Go
* @date          : 2023.09.24
* @description   : 사용자 스케줄 관리 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.09.24        ghgo       최초 생성
 */
@Tag(name="사용자 스케줄 관리 컨트롤러")
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class DailyRoutineUserController extends BaseModuleController{

	/**스케줄 서비스*/
	private final DailyRoutineService dailyRoutineService;
	
	/**회원 서비스*/
	private final MemberService memberService;
	
	/**친구목록 서비스  */
	private final FriendListService friendListService;
	
	/**회원 로그인 세션*/
	private final MemberSession memberSession;

	/**
	 * 스케줄 달력 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 달력 목록 조회")
	@Parameter(name = "toDate", description = "날짜 ex) yyyy-MM-dd, 만약 date에 빈값일 경우 오늘 날짜 기준으로 조회해옴", required = false)
	@GetMapping(API_URL+"/daily/routine/calendar")
	public List<Map<String,Object>> selectCalendarList(@Parameter(hidden = true) DailyRoutineDefaultDto searchDto) throws Exception {
		searchDto.setMemberId(memberSession.getMemberSession().getId());
		//초기값 세팅 date가 0일경우
		if(StringUtils.isBlank(searchDto.getToDate())){
			searchDto.setToDate(LocalDate.now().toString());
		}
		//캘린더에 일정 정보가 존재하는지 표시할 데이터
		return dailyRoutineService.selectDailyRoutineCalendarDataExistList(searchDto);
	}
	
	/**
	 * 스케줄 목록 조회
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 목록 조회")
	@Parameters({
			@Parameter(name = "toDate", description = "날짜 ex) yyyy-MM-dd, 만약 date에 빈값일 경우 오늘 날짜 기준으로 조회해옴", required = false),
			@Parameter(name = "page", description = "페이지 번호", required = false)
	})
	@GetMapping(API_URL+"/daily/routine/list")
	public Page<DailyRoutineSummaryResponse> selectDailyRoutineList(@Parameter(hidden = true) DailyRoutineDefaultDto searchDto) throws Exception {

		searchDto.setMemberId(memberSession.getMemberSession().getId());
		//초기값 세팅 date가 0일경우
		if(searchDto.getToDate().isEmpty()){
			searchDto.setToDate(LocalDate.now().toString());
		}

		return dailyRoutineService.selectDailyRoutinePageList(searchDto);
	}
	
	/**
	 * 스케줄 상세 목록 조회
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 기본 상세정보 및 타임라인 목록 조회")
	@Parameters({
			@Parameter(name = "dailyIdx", description = "스케줄 부모 idx", required = false),
			@Parameter(name = "page", description = "페이지 번호", required = false)
	})
	@GetMapping(API_URL+"/daily/routine/view")
	public DailyRoutineViewWithInviteListResponse selectDailyRoutineList(@Parameter(hidden = true) DailyRoutineInviteDto searchDto) throws Exception {

		//초대인원
		List<DailyRoutineInviteResponse> invites = dailyRoutineService.selectDailyRoutineInviteList(searchDto);

		//일정 정보
		DailyRoutineDto dailyRoutineDto = new DailyRoutineDto();
		dailyRoutineDto.setIdx(searchDto.getDailyIdx());
		dailyRoutineDto = dailyRoutineService.selectDailyRoutineView(dailyRoutineDto);

		return DailyRoutineViewWithInviteListResponse.responseViewWithInvites(dailyRoutineDto,invites);
	}

	@Operation(summary="사용자 스케줄 타임라인 목록 조회")
	@Parameter(name = "dailyIdx", description = "스케줄 부모 idx", required = false)
	@GetMapping(API_URL+"/daily/routine/time/line/list")
	public Map<String,Object> selectDailyRoutineTimeLineList(@Parameter(hidden = true) DailyRoutineTimeLineDefaultDto searchDto) throws Exception {

		//일정 타임라인 정보 조회
		List<DailyRoutineTimeLineDto> list = dailyRoutineService.selectDailyRoutineTimeLineList(searchDto);
		List<Map<String,Object>> resultList = list.stream().map(DailyRoutineTimeLineDto::toMap).toList();

		int totalCost = 0;
		for(Map<String,Object> map : resultList) {
			totalCost += (int)map.get("cost");
		}

		//날짜별 정렬하여 전달
		TreeMap<String,List<Map<String,Object>>> resultMap = resultList.stream().collect(Collectors.groupingBy(x-> x.get("applyDate").toString(),
				TreeMap::new,
				Collectors.toList()));

		//일정 타임라인 정보
		modelMap.put("timeList", resultMap);
		modelMap.put("totalCost",totalCost);

		return modelMap;
	}
	
	/**
	 * 타임라인 상세 정보 조회
	 * @param timeLineDto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 타임라인 상세 조회")
	@Parameter(name = "idx", description = "스케줄 자식 idx", required = false)
	@GetMapping(API_URL+"/daily/routine/time/line/view")
	public DailyRoutineTimeLineDto selectDailyRoutineTimelineView(@Parameter(hidden = true) DailyRoutineTimeLineDto timeLineDto) throws Exception {
		return dailyRoutineService.selectDailyRoutineTimeLineView(timeLineDto);
	}


	/**
	 * 스케줄 명 
	 * @param dailyRoutineCreateRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="일정 등록" ,description = "타임라인 등록전 대표일정 등록")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "등록 완료"),
			@ApiResponse(responseCode = "422", description = "등록이 이루어지지 않음"),
			@ApiResponse(responseCode = "400", description = "등록 오류 발생")
	})
	@PostMapping(API_URL+"/daily/routine/ins")
	public ResponseEntity<?> insertDailyRoutineBatch(final @Valid @RequestBody DailyRoutineCreateRequest dailyRoutineCreateRequest) throws Exception {
		RoutineServiceResult<?> result = null;
		try {
			DailyRoutineDto dailyRoutineDto = DailyRoutineDto.createOf(dailyRoutineCreateRequest);
			dailyRoutineDto.setMemberId(memberSession.getMemberSession().getId());
			result = dailyRoutineService.insertDailyRoutine(dailyRoutineDto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("insert daily routine info and timeline error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("등록시 오류가 발생했습니다."),HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.OK);
	}

	@Operation(summary="일정 수정" ,description = "타임라인 등록전 대표일정 수정")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "수정 완료"),
			@ApiResponse(responseCode = "422", description = "수정이 이루어지지 않음"),
			@ApiResponse(responseCode = "400", description = "수정 오류 발생")
	})
	@PutMapping(API_URL+"/daily/routine/upd")
	public ResponseEntity<?> updateDailyRoutineBatch(final @Valid @RequestBody DailyRoutineUpdateRequest dailyRoutineUpdateRequest) throws Exception {
		RoutineServiceResult<?> result = null;
		try {
			DailyRoutineDto dailyRoutineDto = DailyRoutineDto.updateOf(dailyRoutineUpdateRequest);
			dailyRoutineDto.setMemberId(memberSession.getMemberSession().getId());
			result = dailyRoutineService.updateDailyRoutine(dailyRoutineDto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("update daily routine info and timeline error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("수정시 오류가 발생했습니다."),HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.OK);
	}
	
	/**
	 * 스케줄 삭제 및 타임라인 삭제
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="스케줄명 및 타임라인 일괄 삭제")
	@Parameter(name = "idx", description = "일련번호")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "삭제 완료"),
			@ApiResponse(responseCode = "422", description = "삭제가 이루어지지 않음"),
			@ApiResponse(responseCode = "400", description = "삭제 오류"),
	})
	@DeleteMapping(API_URL+"/daily/routine/del")
	public ResponseEntity<?> deleteDailyRoutine(final @Valid @RequestBody DailyRoutineDeleteRequest dailyRoutineDeleteRequest) throws Exception {
		
		RoutineServiceResult<?> result = null;
		try {
			DailyRoutineDto dto = new DailyRoutineDto();
			dto.setIdx(dailyRoutineDeleteRequest.getIdx());
			result = dailyRoutineService.deleteDailyRoutine(dto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("delete daily routine error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("삭제시 오류가 발생했습니다."),HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.OK);
	}
	
	/**
	 * 타임라인별 등록
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="타임라인별 등록")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "등록 완료"),
			@ApiResponse(responseCode = "422", description = "등록 이루어지지 않음"),
			@ApiResponse(responseCode = "400", description = "등록 오류 발생")
	})
	@PostMapping(value=API_URL+"/daily/routine/time/line/act/ins")
	public ResponseEntity<?> insertDailyRoutineTimeLine(final @Valid @RequestBody DailyRoutineTimeLineCreateRequest dailyRoutineTimeLineCreateRequest) throws Exception {
		
		try {
			DailyRoutineTimeLineDto dailyRoutineTimeLineDto = DailyRoutineTimeLineDto.createOf(dailyRoutineTimeLineCreateRequest);
			if(!dailyRoutineService.insertDailyRoutineTimeLine(dailyRoutineTimeLineDto)) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("등록 되지 않았습니다. 다시 진행해주시기 바랍니다."),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("insert daily routine time line error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("등록시 오류가 발생했습니다."),HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("등록 되었습니다."),HttpStatus.OK);
	}
	
	/**
	 * 타임라인별 수정
	 * @param dailyRoutineTimeLineUpdateRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="타임라인별 수정")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "수정 완료"),
			@ApiResponse(responseCode = "422", description = "수정이 이루어지지 않음"),
			@ApiResponse(responseCode = "400", description = "수정 오류")
	})
	@PutMapping(value=API_URL+"/daily/routine/time/line/act/upd")
	public ResponseEntity<?> updateDailyRoutineTimeLine(final @Valid @RequestBody DailyRoutineTimeLineUpdateRequest dailyRoutineTimeLineUpdateRequest) throws Exception {
		
		RoutineServiceResult<?> result = null;
		try {
			DailyRoutineTimeLineDto dailyRoutineTimeLineDto = DailyRoutineTimeLineDto.updateOf(dailyRoutineTimeLineUpdateRequest);
			result = dailyRoutineService.updateDailyRoutineTimeLine(dailyRoutineTimeLineDto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("update daily routine timeline error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("수정시 오류가 발생했습니다."),HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.OK);
	}
	
	/**
	 * 타임라인별 삭제
	 * @param dailyRoutineTimeLineDeleteRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="타임라인별 삭제")
	@Parameter(name = "idx", description="일련번호")
	@ApiResponses(value= {
			@ApiResponse(responseCode = "200", description = "삭제 완료"),
			@ApiResponse(responseCode = "422", description = "삭제 오류")
	})
	@DeleteMapping(value=API_URL+"/daily/routine/time/line/act/del")
	public ResponseEntity<?> deleteDailyRoutineTimeLine(final @Valid @RequestBody DailyRoutineTimeLineDeleteRequest dailyRoutineTimeLineDeleteRequest) throws Exception {
		
		try {		
			DailyRoutineTimeLineDto dto = new DailyRoutineTimeLineDto();
			dto.setIdx(dailyRoutineTimeLineDeleteRequest.getIdx());
			dailyRoutineService.deleteDailyRoutineTimeLine(dto);
		}catch (Exception e) {
			logger.error("delete daily timeline error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("삭제시 오류가 발생했습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("삭제 되었습니다."),HttpStatus.OK);
	}

	/**
	 * 공개 범위 설정
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "공개 범위 설정")
	@Parameters({
			@Parameter(name = "idx", description = "일정 일련번호"),
			@Parameter(name = "rangeType", description = "범위 설정 ex) PUBLIC : 공개, PRIVATE : 비공개 ")
	})
	@ApiResponses(value= {
			@ApiResponse(responseCode = "200", description = "설정 완료"),
			@ApiResponse(responseCode = "422", description = "공개 범위 설정 실패"),
			@ApiResponse(responseCode = "400", description = "공개 범위 설정 오류")
	})
	@PutMapping(API_URL+"/daily/routine/config/range/change")
	public ResponseEntity<?> updateDailyRoutineConfigRangeChange(final @Valid @RequestBody DailyRoutineChangeRangeTypeRequest dailyRoutineChangeRangeTypeRequest) throws Exception {

		try{
			DailyRoutineDto dailyRoutineDto = DailyRoutineDto.changeRangeTypeOf(dailyRoutineChangeRangeTypeRequest);
			boolean result = dailyRoutineService.dailyRoutineRangeTypeChange(dailyRoutineDto);
			if(!result){
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("공개 범위 설정에 실패하였습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("### daily routine config range change error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("공개 범위 설정시 오류가 발생하였습니다."),HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(RoutineRangeConfigType.valueOf(dailyRoutineChangeRangeTypeRequest.getRangeType()).getDisplay()+" 설정되었습니다."),HttpStatus.OK);
	}
	
	/**
	 * 멤버 리스트
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="회원목록 및 친구목록")
	@Parameters( value = {
			@Parameter(name = "sstring", description = "검색어", required = false),
			@Parameter(name = "page", description = "페이지", required = false)
	})
	@GetMapping(value=API_URL+"/daily/routine/invite/list")
	public MemberListAndFriendListResponse selectInviteList(@Parameter(hidden = true) MemberDefaultDto searchDto) throws Exception {
		
		//회원목록
		searchDto.setStype("nickname");
		searchDto.setExclude(true);
		searchDto.setExcludeType("myself");
		searchDto.setMemberId(memberSession.getMemberSession().getId());
		Page<MemberSummaryResponse> memberList = memberService.selectMemberPageList(searchDto);
		
		//친구목록
		MemberFriendsDto friendsDto = new MemberFriendsDto();
		friendsDto.setMemberId(memberSession.getMemberSession().getId());
		friendsDto.setBlockYn("N");
		List<MemberFriendsResponse> friendList = friendListService.selectFriendListResultList(friendsDto);
		
		return new MemberListAndFriendListResponse(memberList,friendList);
	}

	/**
	 * 친구 목록
	 * @param inviteDto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "친구 초대 목록")
	@Parameter(name = "dailyIdx" , description = "일정 일련번호")
	@GetMapping(value=API_URL+"/daily/routine/invite/friend/list")
	public List<DailyRoutineInviteResponse> selectDailyRoutineInviteList(@Parameter(hidden = true) DailyRoutineInviteDto inviteDto) throws Exception {
//		inviteDto.setMemberId(memberSession.getMemberSession().getId());
		return dailyRoutineService.selectDailyRoutineInviteList(inviteDto);
	}

	/**
	 * 친구 초대 목록
	 */
	
	/**
	 * 친구 초대
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="친구 초대")
	@Parameters(value={
			@Parameter(name = "dailyIdx", description = "부모 일련번호"),
			@Parameter(name = "memberId", description = "초대 아이디"),
	})
	@PostMapping(value=API_URL+"/daily/routine/invite/ins")
	public ResponseEntity<?> insertDailyRoutineInvite(final @Valid @RequestBody DailyRoutineInviteCreateRequest dailyRoutineInviteCreateRequest) throws Exception {
		RoutineServiceResult<?> result = null;
		try {
			result = dailyRoutineService.insertDailyRoutineInvite(DailyRoutineInviteDto.createOf(dailyRoutineInviteCreateRequest));
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error(" insert daily routine error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("초대시 에러가 발생했습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf(result.getMessage()),HttpStatus.OK);
	}
	
	/**
	 * 친구 초대 삭제
	 * @param dailyRoutineInviteDeleteRequest
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="친구 초대 삭제")
	@Parameter(name = "idx", description="일련번호")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "친구 초대 삭제 완료"),
			@ApiResponse(responseCode = "422", description = "친구 삭제 오류")
	})
	@DeleteMapping(value=API_URL+"/daily/routine/invite/del")
	public ResponseEntity<?> deleteDailyRoutineInvite(final @Valid @RequestBody DailyRoutineInviteDeleteRequest dailyRoutineInviteDeleteRequest) throws Exception {
		
		try {
			DailyRoutineInviteDto dto = new DailyRoutineInviteDto();
			dto.setIdx(dailyRoutineInviteDeleteRequest.getIdx());
			dailyRoutineService.deleteDailyRoutineInvite(dto);
		}catch (Exception e) {
			logger.error("delete daily routine invite error : {}",e.getMessage());
			return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("초대 삭제시 에러가 발생했습니다."),HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("삭제 되었습니다."),HttpStatus.OK);
	}

	/**
	 * 일정 지도
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "일정 지도 보기")
	@Parameters({
			@Parameter(name="dailyIdx", description = "일정 일련번호"),
			@Parameter(name="applyDate", description = "일정 적용일자")
	})
	@GetMapping(API_URL+"/daily/routine/plan/map")
	public List<Map<String,Object>> selectDailyRoutinePlanMap(@Parameter(hidden = true) DailyRoutineTimeLineDefaultDto searchDto) throws Exception {
		List<DailyRoutineTimeLineDto> resultList = dailyRoutineService.selectDailyRoutineTimeLineList(searchDto);
		return resultList.stream().map(DailyRoutineTimeLineDto::toMap).toList();
	}
}
