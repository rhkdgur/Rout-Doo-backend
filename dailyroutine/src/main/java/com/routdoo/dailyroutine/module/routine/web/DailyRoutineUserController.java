package com.routdoo.dailyroutine.module.routine.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.*;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.routine.RoutineResultCodeType;
import com.routdoo.dailyroutine.module.routine.RoutineServiceResult;
import com.routdoo.dailyroutine.module.routine.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
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
@CrossOrigin("*")
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
	 * 스케줄 목록 조회
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 목록 조회")
	@Parameter(name = "date", description = "날짜 ex) yyyy-MM-dd, 만약 date에 빈값일 경우 오늘 날짜 기준으로 조회해옴")
	@GetMapping(API_URL+"/daily/routine/list")
	public DailyRoutineListWithCalendarListResponse selectDailyRoutineList(
													@RequestParam(value="date",defaultValue = "") String date,
													@RequestParam(value="page",defaultValue = "1") int page
													) throws Exception {
		
		DailyRoutineDefaultDto searchDto = new DailyRoutineDefaultDto();
		searchDto.setMemberId(memberSession.getMemberSession().getId());
		searchDto.setPage(page);
		//초기값 세팅 date가 0일경우
		if(date.isEmpty()){
			searchDto.setToDate(LocalDate.now().toString());
		}else {
			searchDto.setToDate(date);
		}
		
		//캘린더에 일정 정보가 존재하는지 표시할 데이터
		List<Map<String,Object>> calendarList = dailyRoutineService.selectDailyRoutineCalendarDataExistList(searchDto);
		Page<DailyRoutineSummaryResponse> list = dailyRoutineService.selectDailyRoutinePageList(searchDto);
		
		return DailyRoutineListWithCalendarListResponse.of(list,calendarList);
	}
	
	/**
	 * 스케줄 상세 목록 조회
	 * @param dailyIdx
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 기본 상세정보 및 타임라인 목록 조회")
	@Parameter(name = "idx", description = "스케줄 부모 idx")
	@GetMapping(API_URL+"/daily/routine/view")
	public DailyRoutineViewWithInviteListResponse selectDailyRoutineList(@RequestParam("idx") Long dailyIdx,
													 @RequestParam(value="page", defaultValue = "1") int page ) throws Exception {

		//초대인원
		DailyRoutineInviteDto inviteDto = new DailyRoutineInviteDto();
		inviteDto.setDailyIdx(dailyIdx);
		List<DailyRoutineInviteResponse> invites = dailyRoutineService.selectDailyRoutineInviteList(inviteDto);

		//일정 정보
		DailyRoutineDto dailyRoutineDto = new DailyRoutineDto();
		dailyRoutineDto.setIdx(dailyIdx);
		dailyRoutineDto = dailyRoutineService.selectDailyRoutineView(dailyRoutineDto);

		return DailyRoutineViewWithInviteListResponse.responseViewWithInvites(dailyRoutineDto,invites);
	}

	@Operation(summary="사용자 스케줄 타임라인 목록 조회")
	@Parameter(name = "dailyIdx", description = "스케줄 부모 idx")
	@GetMapping(API_URL+"/daily/routine/time/line/list")
	public Map<String,Object> selectDailyRoutineTimeLineList(@RequestParam("dailyIdx") Long dailyIdx) throws Exception {

		//일정 타임라인 정보 조회
		DailyRoutineTimeLineDefaultDto searchDto = new DailyRoutineTimeLineDefaultDto();
		searchDto.setDailyIdx(dailyIdx);
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
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 타임라인 상세 조회")
	@Parameter(name = "idx", description = "스케줄 자식 idx")
	@GetMapping(API_URL+"/daily/routine/time/line/view")
	public DailyRoutineTimeLineDto selectDailyRoutineTimelineView(@RequestParam("idx") Long idx) throws Exception {
		DailyRoutineTimeLineDto timeLineDto = new DailyRoutineTimeLineDto();
		timeLineDto.setIdx(idx);
		timeLineDto = dailyRoutineService.selectDailyRoutineTimeLineView(timeLineDto);
		return timeLineDto;
	}


	/**
	 * 스케줄 명 
	 * @param dailyRoutineDto
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
	public ResponseEntity<String> insertDailyRoutineBatch(final @Valid @RequestBody  DailyRoutineDto dailyRoutineDto) throws Exception {
		RoutineServiceResult<?> result = null;
		try {		
			dailyRoutineDto.setMemberId(memberSession.getMemberSession().getId());
			result = dailyRoutineService.insertDailyRoutine(dailyRoutineDto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("insert daily routine info and timeline error : {}",e.getMessage());
			return new ResponseEntity<String>("등록시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
	}

	@Operation(summary="일정 수정" ,description = "타임라인 등록전 대표일정 수정")
	@ApiResponses(value={
			@ApiResponse(responseCode = "200", description = "수정 완료"),
			@ApiResponse(responseCode = "422", description = "수정이 이루어지지 않음"),
			@ApiResponse(responseCode = "400", description = "수정 오류 발생")
	})
	@PutMapping(API_URL+"/daily/routine/upd")
	public ResponseEntity<String> updateDailyRoutineBatch(final @Valid @RequestBody DailyRoutineDto dailyRoutineDto) throws Exception {
		RoutineServiceResult<?> result = null;
		try {
			dailyRoutineDto.setMemberId(memberSession.getMemberSession().getId());
			result = dailyRoutineService.insertDailyRoutine(dailyRoutineDto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("update daily routine info and timeline error : {}",e.getMessage());
			return new ResponseEntity<String>("수정시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
	}
	
	/**
	 * 스케줄 삭제 및 타임라인 삭제
	 * @param idx
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
	public ResponseEntity<String> deleteDailyRoutine(@RequestParam("idx") Long idx) throws Exception {
		
		RoutineServiceResult<?> result = null;
		try {
			
			DailyRoutineDto dto = new DailyRoutineDto();
			dto.setIdx(idx);
			result = dailyRoutineService.deleteDailyRoutine(dto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("delete daily routine error : {}",e.getMessage());
			return new ResponseEntity<String>("삭제시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
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
	public ResponseEntity<String> insertDailyRoutineTimeLine(final @Valid @RequestBody DailyRoutineTimeLineDto dailyRoutineTimeLineDto) throws Exception {
		
		try {
			
			if(!dailyRoutineService.insertDailyRoutineTimeLine(dailyRoutineTimeLineDto)) {
				return new ResponseEntity<String>("등록 되지 않았습니다. 다시 진행해주시기 바랍니다.",HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("insert daily routine time line error : {}",e.getMessage());
			return new ResponseEntity<String>("등록시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("등록 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 타임라인별 수정
	 * @param dailyRoutineTimeLineDto
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
	public ResponseEntity<String> updateDailyRoutineTimeLine(final @Valid @RequestBody DailyRoutineTimeLineDto dailyRoutineTimeLineDto) throws Exception {
		
		RoutineServiceResult<?> result = null;
		try {

			result = dailyRoutineService.updateDailyRoutineTimeLine(dailyRoutineTimeLineDto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("update daily routine timeline error : {}",e.getMessage());
			return new ResponseEntity<String>("수정시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
	}
	
	/**
	 * 타임라인별 삭제
	 * @param idx
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
	public ResponseEntity<String> deleteDailyRoutineTimeLine(@RequestParam("idx") Long idx) throws Exception {
		
		try {		
			DailyRoutineTimeLineDto dto = new DailyRoutineTimeLineDto();
			dto.setIdx(idx);
			dailyRoutineService.deleteDailyRoutineTimeLine(dto);
		}catch (Exception e) {
			logger.error("delete daily timeline error : {}",e.getMessage());
			return new ResponseEntity<String>("삭제시 오류가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<String>("삭제 되었습니다.",HttpStatus.OK);
	}

	/**
	 * 공개 범위 설정
	 * @param idx
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
	public ResponseEntity<String> updateDailyRoutineConfigRangeChange(
											@RequestParam("idx") Long idx,
											@RequestParam("rangeType") String rangeType) throws Exception {

		try{
			DailyRoutineDto dailyRoutineDto = new DailyRoutineDto();
			dailyRoutineDto.setIdx(idx);
			dailyRoutineDto.setRangeType(rangeType);
			boolean result = dailyRoutineService.dailyRoutineRangeTypeChange(dailyRoutineDto);
			if(!result){
				return new ResponseEntity<>("공개 범위 설정에 실패하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error("### daily routine config range change error : {}",e.getMessage());
			return new ResponseEntity<>("공개 범위 설정시 오류가 발생하였습니다.",HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(RoutineRangeConfigType.valueOf(rangeType).getDisplay()+" 설정되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 멤버 리스트
	 * @param dailyIdx
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="회원목록 및 친구목록")
	@Parameters( value = {
		@Parameter(name = "name", description="이름"),
		@Parameter(name = "dailyIdx", description="부모 일련번호"),
		@Parameter(name = "page", description="페이지")
	})
	@GetMapping(value=API_URL+"/daily/routine/invite/list")
	public MemberListAndFriendListResponse selectInviteList(
												@RequestParam(value="name",required = false) String name,
												@RequestParam("idx") Long dailyIdx,
												@RequestParam(value="page",defaultValue = "1") int page) throws Exception {
		
		//회원목록  
		MemberDefaultDto searchDto = new MemberDefaultDto();
		searchDto.setStype("name");
		searchDto.setSstring(name);
		searchDto.setPage(page);
		Page<MemberSummaryResponse> memberList = memberService.selectMemberPageList(searchDto);
		
		//친구목록
		MemberFriendsDto friendsDto = new MemberFriendsDto();
		friendsDto.setMemberId("");
		friendsDto.setBlockYn("N");
		List<MemberFriendsResponse> friendList = friendListService.selectFriendListResultList(friendsDto);
		
		return new MemberListAndFriendListResponse(memberList,friendList);
	}
	
	/**
	 * 친구 초대
	 * @param dailyIdx
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="친구 초대")
	@Parameters(value={
		@Parameter(name = "dailyIdx", description="부모 일련번호"), @Parameter(name = "memberId", description="초대 아이디"),
	})
	@PostMapping(value=API_URL+"/daily/routine/invite/ins")
	public ResponseEntity<String> insertDailyRoutineInvite(@RequestParam(value = "dailyIdx") Long dailyIdx,
														   @RequestParam(value = "memberId") String memberId) throws Exception {
		RoutineServiceResult<?> result = null;
		try {
			DailyRoutineInviteDto dto = new DailyRoutineInviteDto();
			dto.setDailyIdx(dailyIdx);
			dto.setMemberId(memberId);
			result = dailyRoutineService.insertDailyRoutineInvite(dto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}catch (Exception e) {
			logger.error(" insert daily routine error : {}",e.getMessage());
			return new ResponseEntity<String>("초대시 에러가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
	}
	
	/**
	 * 친구 초대 삭제
	 * @param idx
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
	public ResponseEntity<?> deleteDailyRoutineInvite(@RequestParam("idx") Long idx) throws Exception {
		
		try {
			DailyRoutineInviteDto dto = new DailyRoutineInviteDto();
			dto.setIdx(idx);
			dailyRoutineService.deleteDailyRoutineInvite(dto);
		}catch (Exception e) {
			logger.error("delete daily routine invite error : {}",e.getMessage());
			return new ResponseEntity<String>("초대 삭제시 에러가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<String>("삭제 되었습니다.",HttpStatus.OK);
	}

	/**
	 * 일정 지도
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "일정 지도 보기")
	@Parameter(name="dailyIdx", description = "일정 일련번호")
	@GetMapping(API_URL+"/daily/routine/plan/map")
	public Map<String, Object> selectDailyRoutinePlanMap(@RequestParam("dailyIdx") Long idx,
														 @RequestParam(value = "applyDate", defaultValue = "") String applyDate) throws Exception {

		DailyRoutineTimeLineDefaultDto searchDto = new DailyRoutineTimeLineDefaultDto();
		searchDto.setDailyIdx(idx);
		searchDto.setApplyDate(applyDate);

		List<DailyRoutineTimeLineDto> resultList = dailyRoutineService.selectDailyRoutineTimeLineList(searchDto);
		List<Map<String,Object>> resultMap = resultList.stream().map(DailyRoutineTimeLineDto::toMap).toList();

		modelMap.put("mapList", resultMap);

		return modelMap;
	}
}
