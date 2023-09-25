package com.routdoo.dailyroutine.module.routine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.repository.PlaceRepository;
import com.routdoo.dailyroutine.module.routine.RoutineResultCodeType;
import com.routdoo.dailyroutine.module.routine.RoutineServiceResult;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineTimeLine;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDto;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineRepository;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineTimeLineRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.service
* @fileName      : DailyRoutineService.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.13
* @description   : 일상 스케줄 service
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.13        ghgo       최초 생성
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyRoutineService {

	private final DailyRoutineRepository dailyRoutineRepository;
	
	private final DailyRoutineTimeLineRepository dailyRoutineTimeLineRepository;
	
	private final PlaceRepository placeRepository;
	
	/**
	 * 일상 정보 페이징(0)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<DailyRoutineDto> selectDailyRoutinePageList(DailyRoutineDefaultDto searchDto) throws Exception {
		return dailyRoutineRepository.selectDailyRoutinePageList(searchDto);
	}
	
	/**
	 * 스케줄 정보 페이징(x)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<DailyRoutineDto> selectDailyRoutineList(DailyRoutineDefaultDto searchDto) throws Exception {
		return dailyRoutineRepository.selectDailyRoutineList(searchDto);
	}
	
	/**
	 * 일상 정보 개수
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Long selectDailyRoutineTotalCount(DailyRoutineDefaultDto searchDto) throws Exception {
		return dailyRoutineRepository.selectDailyRoutineTotalCount(searchDto);
	}
	
	/**
	 * 일상 정보 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DailyRoutineDto selectDailyRoutineView(DailyRoutineDto dto) throws Exception {
		
		DailyRoutine dr = dailyRoutineRepository.findById(dto.getIdx()).orElse(null);
		if(dr != null) {
			DailyRoutineTimeLineDefaultDto searchDto = new DailyRoutineTimeLineDefaultDto();
			searchDto.setDailyIdx(dto.getIdx());
			List<DailyRoutineTimeLineDto> list = dailyRoutineRepository.selectDailyRoutineTimeLineList(searchDto);
			dto = new DailyRoutineDto(dr);
			dto.setTimeList(list);
		}else {
			return null;
		}
		return dto;
	}
	
	/**
	 * 일상 정보 상세 정보 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<DailyRoutineTimeLineDto> selectDailyRoutineTimeLineList(DailyRoutineTimeLineDefaultDto searchDto) throws Exception {
		return dailyRoutineRepository.selectDailyRoutineTimeLineList(searchDto);
	}
	
	/**
	 * 일상 정보 상세 정보
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DailyRoutineTimeLineDto selectDailyRoutineTimeLineView(DailyRoutineTimeLineDto dto) throws Exception {
		DailyRoutineTimeLine timeLine =  dailyRoutineTimeLineRepository.findById(dto.getIdx()).orElse(null);
		return new DailyRoutineTimeLineDto(timeLine);
	}
	
	/**
	 * 일상 스케줄 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public RoutineServiceResult<?> insertDailyRoutine(DailyRoutineDto dto) throws Exception {
		
		DailyRoutine dailyRoutine = new DailyRoutine(dto);
		
		List<String> placeNumList = new ArrayList<String>(); 
		for(DailyRoutineTimeLineDto line : dto.getTimeList()) {
			if(line.getPlaceDto() != null) {
				if(line.getPlaceDto().getPlaceNum() != null && !line.getPlaceDto().getPlaceNum().isEmpty()) {
					placeNumList.add(line.getPlaceDto().getPlaceNum());
				}
			}
		}
		
		List<Place> placeList = placeRepository.selectPlaceNumListIn(placeNumList);
		
		for(DailyRoutineTimeLineDto line : dto.getTimeList()) {
			Place place = new Place();
			if(RoutineWriteType.SEARCH.name().equals(line.getWriteType())) {
				place = placeList.stream().filter(x-> x.getPlaceNum().equals(line.getPlaceDto().getPlaceNum())).findFirst().orElse(null);
				if(place == null) {
					return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"잘못된 접근입니다.");
				}
			}
			DailyRoutineTimeLine timeLine = new DailyRoutineTimeLine(line,place);
			dailyRoutine.addDailyRoutineTimeLine(timeLine);
		}
		
		//등록
		if(dailyRoutineRepository.save(dailyRoutine) == null) {
			return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"일정 등록에 실패하였습니다.");
		}
		
		return new RoutineServiceResult<>(RoutineResultCodeType.OK);
	}
	
	/**
	 * 일상 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public RoutineServiceResult<?> deleteDailyRoutine(DailyRoutineDto dto) throws Exception {
		
		DailyRoutine dr = dailyRoutineRepository.findById(dto.getIdx()).orElse(null);
		if(dr == null) {
			return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"잘못된 접근입니다.");
		}
		dailyRoutineRepository.delete(dr);
		return new RoutineServiceResult<>(RoutineResultCodeType.OK,"삭제 되었습니다.");
	}
	
	/**
	 * 일정 타임라인 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean insertDailyRoutineTimeLine(DailyRoutineTimeLineDto dto) throws Exception {
		return dailyRoutineTimeLineRepository.save(dto.toEntity()) != null ? true : false;
	}
	
	/**
	 * 일정 타임라인 수정
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public RoutineServiceResult<?> updateDailyRoutineTimeLine(DailyRoutineTimeLineDto dto) throws Exception {
		DailyRoutineTimeLine timeLine = dailyRoutineTimeLineRepository.findById(dto.getIdx()).orElse(null);
		if(timeLine == null) {
			return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"잘못된 접근입니다.");
		}
		timeLine.addDailRoutineTimeLine(dto);
		return new RoutineServiceResult<>(RoutineResultCodeType.OK,"수정 되었습니다.");
	}
	
	/**
	 * 일정 타임라인 삭제
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void deleteDailyRoutineTimeLine(DailyRoutineTimeLineDto dto) throws Exception {
		dailyRoutineTimeLineRepository.deleteById(dto.getIdx());
	}
}
