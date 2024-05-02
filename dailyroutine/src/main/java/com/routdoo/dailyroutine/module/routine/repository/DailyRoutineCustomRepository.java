package com.routdoo.dailyroutine.module.routine.repository;

import com.routdoo.dailyroutine.module.routine.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

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
	Page<DailyRoutineSummaryResponse> selectDailyRoutinePageList(DailyRoutineDefaultDto searchDto);
	
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

	/**
	 * 공개 범위 지정 상태 변경
	 * @param dailyRoutineDto
	 * @return
	 * @throws Exception
	 */
	boolean updateDailyRoutineRangeChange(DailyRoutineDto dailyRoutineDto) throws Exception;


	/**
	 * 좋아요 정보 목록 페이징
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<DailyRoutineDto> selectDailyRoutineLikePageList(DailyRoutineLikeDefaultDto searchDto) throws Exception;


	/**
	 * 좋아요 정보 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	DailyRoutineLikeDto selectDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception;


	/**
	 * 일정 좋아요 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	boolean insertDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception;

	/**
	 * 일정 좋아요 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long deleteDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception;


	boolean updateDailyRoutinePublicYn(DailyRoutineDto dto) throws Exception;
}
