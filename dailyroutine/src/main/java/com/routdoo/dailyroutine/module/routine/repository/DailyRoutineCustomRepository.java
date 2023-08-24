package com.routdoo.dailyroutine.module.routine.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDto;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.repository
* @fileName      : DailyRoutineCustomService.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.13
* @description   : 일상 커스텀 service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.13        ghgo       최초 생성
 */
public interface DailyRoutineCustomRepository {

	/**
	 * 일상 목록 조회(페이징 o)
	 * @param searchDto
	 * @return
	 */
	Page<DailyRoutineDto> selectDailyRoutinePageList(DailyRoutineDefaultDto searchDto);
	
	/**
	 * 일상 목록 조회(페이징 x)
	 * @param searchDto
	 * @return
	 */
	List<DailyRoutineDto> selectDailyRoutineList(DailyRoutineDefaultDto searchDto);
	
	/**
	 * 일상 목록 개수
	 * @param searchDto
	 * @return
	 */
	Long selectDailyRoutineTotalCount(DailyRoutineDefaultDto searchDto);
	
	/**
	 * 일상 상세 정보 목록 조회
	 * @param searchDto
	 * @return
	 */
	Page<DailyRoutineTimeLineDto> selectDailyRoutineTimePageList(DailyRoutineTimeLineDefaultDto searchDto);
	
	/**
	 * 일상 상세 정보 목록 조회
	 * @param searchDto
	 * @return
	 */
	List<DailyRoutineTimeLineDto> selectDailyRoutineTimeLineList(DailyRoutineTimeLineDefaultDto searchDto);
	
}
