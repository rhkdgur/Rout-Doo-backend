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

import com.routdoo.dailyroutine.auth.member.service.MemberService;
import com.routdoo.dailyroutine.common.web.BaseController;
import com.routdoo.dailyroutine.module.routine.RoutineResultCodeType;
import com.routdoo.dailyroutine.module.routine.RoutineServiceResult;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;

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
@RestController
@RequiredArgsConstructor
public class DailyRoutinUserController extends BaseController{

	/**스케줄 서비스*/
	private final DailyRoutineService dailyRoutineService;
	
	/**회원 서비스*/
	private final MemberService memberService;
	
	/**
	 * 스케줄 목록 조회
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/daily/routine/list")
	public Map<String,Object> selectDailyRoutineList(@RequestParam("date") String date) throws Exception {
		
		modelMap = new LinkedHashMap<String, Object>();
		
		DailyRoutineDefaultDto searchDto = new DailyRoutineDefaultDto();
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
	@GetMapping("/daily/routine/view")
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
	@GetMapping("/daily/routine/time/line/view")
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
	@PostMapping(value="/daily/routine/ins")
	public ResponseEntity<String> insertDailyRoutineBatch(DailyRoutineDto dailyRoutineDto) throws Exception {
		RoutineServiceResult<?> result = null;
		try {
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
	@PostMapping(value="/daily/routine/del")
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
	@PostMapping(value="/daily/routine/time/line/act",params="amode=ins")
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
	@PostMapping(value="/daily/routine/time/line/act",params="amode=upd")
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
	@PostMapping(value="/daily/routine/time/line/act",params="amode=del")
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
	@GetMapping(value="/daily/routine/invite/list")
	public Map<String,Object> selectInviteList(@RequestParam("idx") Long dailyIdx) throws Exception {
		
		return null;
	}
}
