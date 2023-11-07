package com.routdoo.dailyroutine.module.routine.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import com.routdoo.dailyroutine.auth.member.service.FriendListService;
import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.routine.RoutineResultCodeType;
import com.routdoo.dailyroutine.module.routine.RoutineServiceResult;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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
@RestController
@RequiredArgsConstructor
public class DailyRoutinUserController extends BaseModuleController{

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
	@Parameter(name = "date", description = "날짜")
	@GetMapping(API_URL+"/daily/routine/list")
	public Map<String,Object> selectDailyRoutineList(
													@RequestParam("date") String date
													) throws Exception {
		
		modelMap = new LinkedHashMap<String, Object>();
		
		DailyRoutineDefaultDto searchDto = new DailyRoutineDefaultDto();
		searchDto.setMemberId(memberSession.getMemberSession().getId());
		searchDto.setToDate(date);
		
		Page<DailyRoutineDto> list = dailyRoutineService.selectDailyRoutinePageList(searchDto);
		modelMap.put("resultList", list);
		
		return modelMap;
	}
	
	/**
	 * 스케줄 상세 목록 조회
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 상세 목록 조회")
	@Parameter(name = "idx", description = "스케줄 부모 idx")
	@GetMapping(API_URL+"/daily/routine/view")
	public Map<String,Object> selectDailyRoutineList(@RequestParam("idx") Long dailyIdx) throws Exception {
		
		modelMap = new LinkedHashMap<String, Object>();
		
		DailyRoutineTimeLineDefaultDto searchDto = new DailyRoutineTimeLineDefaultDto();
		searchDto.setDailyIdx(dailyIdx);
		
		List<DailyRoutineTimeLineDto> list = dailyRoutineService.selectDailyRoutineTimeLineList(searchDto);
		List<Map<String,String>> resultMap = new ArrayList<Map<String,String>>();
		for(DailyRoutineTimeLineDto dto : list) {
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("idx", dto.getIdx()+"");
			map.put("dailyIdx", dto.getDailyIdx()+"");
			map.put("title", dto.getTitle());
			map.put("startTime", dto.getShour()+":"+dto.getSmin());
			map.put("endTime", dto.getEhour()+":"+dto.getEmin());
			map.put("ord", dto.getOrd()+"");
			resultMap.add(map);
		}
		modelMap.put("resultList", resultMap);
		
		return modelMap;
	}
	
	/**
	 * 타임라인 상세 정보 조회
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 스케줄 상세 목록 조회")
	@Parameter(name = "idx", description = "스케줄 자식 idx")
	@GetMapping(API_URL+"/daily/routine/time/line/view")
	public Map<String,Object> selectDailyRoutineTimelineView(@RequestParam("idx") Long idx) throws Exception {
		
		modelMap = new LinkedHashMap<String, Object>();
		
		DailyRoutineTimeLineDto dto = new DailyRoutineTimeLineDto();
		dto.setIdx(idx);
		
		dto = dailyRoutineService.selectDailyRoutineTimeLineView(dto);
		
		modelMap.put("timeLineDto", dto);
		
		return modelMap;
	}
	
	/**
	 * 스케줄 명 및 타임라인 일괄 등록
	 * @param dailyRoutineDto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="스케줄명 및 타임라인 일괄 등록" ,description = "스케줄 타임라인도 같이 전달해주어야함 (리스트로 해서 전달 줘야함) 타임라인 등록 정보 참고")
	@Parameters( value = {
		@Parameter(name = "title", description ="제목"),
		@Parameter(name = "startdate", description ="시작일자"),
		@Parameter(name = "endDate", description ="제목(장소)"),
		@Parameter(name = "dayType", description ="해쉬태그")
	})
	@PostMapping(API_URL+"/daily/routine/ins")
	public ResponseEntity<String> insertDailyRoutineBatch(DailyRoutineDto dailyRoutineDto) throws Exception {
		RoutineServiceResult<?> result = null;
		try {		
			dailyRoutineDto.setMemberId(memberSession.getMemberSession().getId());
			result = dailyRoutineService.insertDailyRoutine(dailyRoutineDto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			logger.error("insert daily routine info and timeline error : {}",e.getMessage());
			return new ResponseEntity<String>("등록시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
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
	@PostMapping(API_URL+"/daily/routine/del")
	public ResponseEntity<String> deleteDailyRoutine(@RequestParam("idx") Long idx) throws Exception {
		
		RoutineServiceResult<?> result = null;
		try {
			
			DailyRoutineDto dto = new DailyRoutineDto();
			dto.setIdx(idx);
			result = dailyRoutineService.deleteDailyRoutine(dto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.BAD_REQUEST);
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
	@Parameters( value = {
		@Parameter(name = "dailyIdx", description ="부모 일련번호"),
		@Parameter(name = "writeType", description ="작성타입"),
		@Parameter(name = "applyDate", description ="적용일자"),
		@Parameter(name = "title", description ="제목"),
		@Parameter(name = "placeName", description ="장소명"),
		@Parameter(name = "ord", description ="순서"),
		@Parameter(name = "context", description ="내용"),
		@Parameter(name = "shour", description ="시작시간"),
		@Parameter(name = "smin", description ="시작분"),
		@Parameter(name = "ehour", description ="마지막시간"),
		@Parameter(name = "emin", description ="마지막분"),
		@Parameter(name = "cost", description ="비용")
	})
	@PostMapping(value=API_URL+"/daily/routine/time/line/act",params="amode=ins")
	public ResponseEntity<String> insertDailyRoutineTimeLine(DailyRoutineTimeLineDto dailyRoutineTimeLineDto) throws Exception {
		
		try {
			
			if(!dailyRoutineService.insertDailyRoutineTimeLine(dailyRoutineTimeLineDto)) {
				return new ResponseEntity<String>("등록 되지 않았습니다. 다시 진행해주시기 바랍니다.",HttpStatus.BAD_REQUEST);
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
	@Parameters(value={
		@Parameter(name = "idx", description = "일련번호"),
		@Parameter(name = "dailyIdx", description="부모 일련번호"),
		@Parameter(name = "writeType", description="작성타입"),
		@Parameter(name = "applyDate", description="적용일자"),
		@Parameter(name = "title", description="제목"),
		@Parameter(name = "placeName", description="장소명"),
		@Parameter(name = "ord", description="순서"),
		@Parameter(name = "context", description="내용"),
		@Parameter(name = "shour", description="시작시간"),
		@Parameter(name = "smin", description="시작분"),
		@Parameter(name = "ehour", description="마지막시간"),
		@Parameter(name = "emin", description="마지막분"),
		@Parameter(name = "cost", description="비용")
	})
	@PostMapping(value=API_URL+"/daily/routine/time/line/act",params="amode=upd")
	public ResponseEntity<String> updateDailyRoutineTimeLine(DailyRoutineTimeLineDto dailyRoutineTimeLineDto) throws Exception {
		
		RoutineServiceResult<?> result = null;
		try {

			result = dailyRoutineService.updateDailyRoutineTimeLine(dailyRoutineTimeLineDto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.BAD_REQUEST);
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
	@PostMapping(value=API_URL+"/daily/routine/time/line/act",params="amode=del")
	public ResponseEntity<String> deleteDailyRoutineTimeLine(@RequestParam("idx") Long idx) throws Exception {
		
		try {		
			DailyRoutineTimeLineDto dto = new DailyRoutineTimeLineDto();
			dto.setIdx(idx);
			dailyRoutineService.deleteDailyRoutineTimeLine(dto);
		}catch (Exception e) {
			logger.error("delete daily timeline error : {}",e.getMessage());
			return new ResponseEntity<String>("삭제시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("삭제 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 멤버 리스트
	 * @param dailyIdx
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="멤버 리스트")
	@Parameters( value = {
		@Parameter(name = "name", description="이름"),
		@Parameter(name = "dailyIdx", description="부모 일련번호"),
		@Parameter(name = "page", description="페이지")
	})
	@GetMapping(value=API_URL+"/daily/routine/invite/list")
	public Map<String,Object> selectInviteList(
												@RequestParam(value="name",required = false) String name,
												@RequestParam("idx") Long dailyIdx,
												@RequestParam(value="page",defaultValue = "1") int page) throws Exception {
		modelMap = new LinkedHashMap<>();
		
		//회원목록  
		MemberDefaultDto searchDto = new MemberDefaultDto();
		searchDto.setStype("name");
		searchDto.setSstring(name);
		searchDto.setPage(page);
		Page<MemberDto> memberList = memberService.selectMemberPageList(searchDto);
		List<Map<String,String>> members = new ArrayList<>();
		for(MemberDto dto : memberList) {
			Map<String,String> map = new LinkedHashMap<>();
			map.put("id", dto.getId());
			map.put("name", dto.getNickname());
			map.put("gender", dto.getGender());
			map.put("age", dto.getAge()+"");
			members.add(map);
		}
		modelMap.put("memberList", members);
		modelMap.put("memberTotalPage", memberList.getTotalPages());
		modelMap.put("memberPage", memberList.getPageable());
		
		//친구목록
		MemberFriendsDto friendsDto = new MemberFriendsDto();
		friendsDto.setMemberId("");
		friendsDto.setBlockYn("N");
		List<MemberFriendsDto> friendList = friendListService.selectFriendListResultList(friendsDto);
		List<Map<String,String>> freinds = new ArrayList<>();
		for(MemberFriendsDto dto : friendList) {
			Map<String,String> map = new LinkedHashMap<>();
			map.put("idx", dto.getIdx()+"");
			map.put("name",dto.getMemberDto().getNickname());
			map.put("gender", dto.getMemberDto().getGender());
			map.put("age",dto.getMemberDto().getAge()+"");
			freinds.add(map);
		}
		modelMap.put("friendList", freinds);
		
		return modelMap;
	}
	
	/**
	 * 친구 초대
	 * @param dailyIdx
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="친구 초대")
	@Parameters(value={
		@Parameter(name = "dailyIdx", description="부모 일련번호"),
		@Parameter(name = "memberId", description="회웡아이디")
	})
	@PostMapping(value=API_URL+"/daily/routine/invite/ins")
	public ResponseEntity<String> insertDailyRoutineInvite(@RequestParam("dailyIdx") Long dailyIdx,
														   @RequestParam("memberId") String memberId) throws Exception {
		RoutineServiceResult<?> result = null;
		try {
			DailyRoutineInviteDto dto = new DailyRoutineInviteDto();
			dto.setDailyIdx(dailyIdx);
			dto.setMemberId(memberId);
			result = dailyRoutineService.insertDailyRoutineInvite(dto);
			if(!RoutineResultCodeType.OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<String>(result.getMessage(),HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			logger.error(" insert daily routine error : {}",e.getMessage());
			return new ResponseEntity<String>("초대시 에러가 발생했습니다.",HttpStatus.BAD_REQUEST);
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
	@PostMapping(value=API_URL+"/daily/routine/invite/del")
	public ResponseEntity<?> deleteDailyRoutineInvite(@RequestParam("idx") Long idx) throws Exception {
		
		try {
			DailyRoutineInviteDto dto = new DailyRoutineInviteDto();
			dto.setIdx(idx);
			dailyRoutineService.deleteDailyRoutineInvite(dto);
		}catch (Exception e) {
			logger.error("delete daily routine invite error : {}",e.getMessage());
			return new ResponseEntity<String>("초대 삭제시 에러가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("삭제 되었습니다.",HttpStatus.OK);
	}
}
