package com.routdoo.dailyroutine.module.routine.service;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.auth.member.repository.MemberRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceRepository;
import com.routdoo.dailyroutine.module.routine.RoutineResultCodeType;
import com.routdoo.dailyroutine.module.routine.RoutineServiceResult;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineInvite;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineTimeLine;
import com.routdoo.dailyroutine.module.routine.dto.*;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineInviteRepository;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineRepository;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineTimeLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
	
	private final DailyRoutineInviteRepository dailyRoutineInviteRepository;
	
	private final PlaceRepository placeRepository;
	
	private final MemberRepository memberRepository;
	
	/**
	 * 일상 정보 페이징(0)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<DailyRoutineSummaryResponse> selectDailyRoutinePageList(DailyRoutineDefaultDto searchDto) throws Exception {
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
//			DailyRoutineTimeLineDefaultDto searchDto = new DailyRoutineTimeLineDefaultDto();
//			searchDto.setDailyIdx(dto.getIdx());
//			List<DailyRoutineTimeLineDto> list = this.selectDailyRoutineTimeLineList(searchDto);
			dto = new DailyRoutineDto(dr);
//			dto.setTimeList(list);
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
	 * 해당 월 달력에 일정 존재 유무 표시 용
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value="daily_routine" , key = "#searchDto.startDate")
	public List<Map<String,Object>> selectDailyRoutineCalendarDataExistList(DailyRoutineDefaultDto searchDto) throws Exception {
		List<Map<String,Object>> resultList = new ArrayList<>();
		String date = searchDto.getToDate();
		if(date.length() > 7){
			date = date.substring(0,7);
		}
		List<Map<String,Object>> list = dailyRoutineRepository.selectDailyRoutineExistList(date,searchDto.getMemberId());
		String month = LocalDate.now().toString().substring(0,7);
		LocalDate startDt = LocalDate.parse(month+"-01");
		LocalDate endDt = startDt.withDayOfMonth(startDt.lengthOfMonth());

		int dayCnt = (int) ChronoUnit.DAYS.between(startDt,endDt) + 1;

		//월 달력 리스트 생성
		List<LocalDate> dtList = IntStream.iterate(0, i->i+1).limit(dayCnt)
				.mapToObj(startDt::plusDays).toList();

		//달력 생성
		for(LocalDate dt : dtList){
			Map<String,Object> map = list.stream().filter(x-> checkRangeDate(x.get("startDate").toString(),x.get("endDate").toString(),dt.toString())).findFirst().orElse(null);
			if(map == null){
				map = getCalendarDefaultMap(dt.toString());
			}else {
				map = getCalendarMap(dt.toString(),map.get("totCnt").toString());
			}
			resultList.add(map);
		}
		
		return resultList;
	}

	/**
	 * 캘린더 날짜별 데이터 개수 초기값 세팅
	 * @param date
	 * @return
	 */
	public Map<String,Object> getCalendarDefaultMap(String date) {
		return getCalendarMap(date,"0");
	}

	public Map<String,Object> getCalendarMap(String date,String totCnt) {
		Map<String,Object> map = new LinkedHashMap<>();
		map.put(date,totCnt);
		return map;
	}

	public boolean checkRangeDate(String startDate, String endDate , String checkDate) {
		LocalDate sdate = LocalDate.parse(startDate);
		LocalDate edate = LocalDate.parse(endDate);
		LocalDate cdate = LocalDate.parse(checkDate);
		return (sdate.equals(cdate) || sdate.isBefore(cdate) ) && (edate.equals(cdate) || edate.isAfter(cdate));
	}

	/**
	 * 공개 범위 변경
	 * @param dailyRoutineDto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean dailyRoutineRangeTypeChange(DailyRoutineDto dailyRoutineDto ) throws Exception {
		return dailyRoutineRepository.updateDailyRoutineRangeChange(dailyRoutineDto);
	}

	/**
	 * 태그 조회 (가장 많이 태그된 정보 10개만 가져옴)
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectDailyRoutineTagMostList() throws Exception {
		return dailyRoutineRepository.selectDailyRoutineTagMostList();
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
		//등록
		DailyRoutine result = dailyRoutineRepository.save(dailyRoutine);
		if(!result.equals(dailyRoutine)){
			return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"일정 등록에 실패하였습니다.");
		}

		return new RoutineServiceResult<>(RoutineResultCodeType.OK);
	}

	
	/**
	 * 일상 스케줄 등록( 타임라인까지 한번에 처리)
	 * @param dto
	 * @return
	 * @throws Exception
	 */
//	@Transactional
//	public RoutineServiceResult<?> insertDailyRoutine2(DailyRoutineDto dto) throws Exception {
//
//		DailyRoutine dailyRoutine = new DailyRoutine(dto);
//
//		//해당 장소 검색
//		List<String> placeNumList = new ArrayList<String>();
//		for(DailyRoutineTimeLineDto line : dto.getTimeList()) {
//			if(line.getPlaceDto() != null) {
//				if(line.getPlaceDto().getPlaceNum() != null && !line.getPlaceDto().getPlaceNum().isEmpty()) {
//					placeNumList.add(line.getPlaceDto().getPlaceNum());
//				}
//			}
//		}
//
//		List<Place> placeList = new ArrayList<>();
//		//장소 목록이 존재 할 경우
//		if(placeNumList.size() > 0) {
//			placeList = placeRepository.selectPlaceNumListIn(placeNumList);
//		}
//
//		for(DailyRoutineTimeLineDto line : dto.getTimeList()) {
//			Place place = new Place();
//			if(RoutineWriteType.SEARCH.name().equals(line.getWriteType())) {
//				place = placeList.stream().filter(x-> x.getPlaceNum().equals(line.getPlaceDto().getPlaceNum())).findFirst().orElse(null);
//				if(place == null) {
//					return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"잘못된 접근입니다.");
//				}
//			}
//			DailyRoutineTimeLine timeLine = new DailyRoutineTimeLine(line,place);
//			dailyRoutine.addDailyRoutineTimeLine(timeLine);
//		}
//
//		//등록
//		if(dailyRoutineRepository.save(dailyRoutine) == null) {
//			return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"일정 등록에 실패하였습니다.");
//		}
//
//		return new RoutineServiceResult<>(RoutineResultCodeType.OK);
//	}
	
	/**
	 * 일상 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@CacheEvict(value="daily_routine" , key = "#dto.startDate")
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

	/**
	 * 초대회원 목록
	 * @param dto
	 * @return
	 */
	public List<DailyRoutineInviteResponse> selectDailyRoutineInviteList(DailyRoutineInviteDto dto) throws Exception {
		return dailyRoutineInviteRepository.selectDailyRoutineInviteDtoList(dto);
	}
	
	/**
	 * 친구 초대 처리
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public RoutineServiceResult<?> insertDailyRoutineInvite(DailyRoutineInviteDto dto) throws Exception {
		//촌재 유무 확인
		DailyRoutine dailyRoutine = dailyRoutineRepository.findById(dto.getDailyIdx()).orElse(null);

		List<DailyRoutineInviteResponse> checklist = dailyRoutineInviteRepository.selectDailyRoutineInviteDtoList(dto);
		if(dto.getMemberIds().size() > 0){
			for(DailyRoutineInviteResponse tmp : checklist){
				for(String id : dto.getMemberIds()) {
					if (tmp.getMemberSummaryResponse().getId().equals(id)){
						return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"이미 초대된 정보가 있습니다.");
					}
				}
			}

			for(String id : dto.getMemberIds()){
				DailyRoutineInvite invite = dto.toEntity();
				Member member = memberRepository.findById(id).orElse(null);
				invite.addDailyRoutineAndMember(dailyRoutine, member);
				dailyRoutineInviteRepository.save(invite);
				if(dailyRoutine == null || member == null) {
					return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"잘못된 접근입니다.");
				}
			}
		}else {
			for (DailyRoutineInviteResponse tmp : checklist) {
				if (tmp.getMemberSummaryResponse().getId().equals(dto.getMemberId())) {
					return new RoutineServiceResult<>(RoutineResultCodeType.FAIL, "이미 초대된 정보입니다.");
				}
			}

			Member member = memberRepository.findById(dto.getMemberId()).orElse(null);
			if(dailyRoutine == null || member == null) {
				return new RoutineServiceResult<>(RoutineResultCodeType.FAIL,"잘못된 접근입니다.");
			}
			DailyRoutineInvite invite = dto.toEntity();
			invite.addDailyRoutineAndMember(dailyRoutine, member);
			dailyRoutineInviteRepository.save(invite);
		}
		return new RoutineServiceResult<>(RoutineResultCodeType.OK,"초대 되었습니다.");
	}
	
	/**
	 * 친구 초대 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void deleteDailyRoutineInvite(DailyRoutineInviteDto dto) throws Exception {
		dailyRoutineInviteRepository.deleteById(dto.getIdx());
	}


	/**
	 * 좋아요 일정 보관 목록 (페이징)
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Page<DailyRoutineSummaryResponse> selectDailyRoutineLikePageList(DailyRoutineLikeDefaultDto searchDto) throws Exception {
		return dailyRoutineRepository.selectDailyRoutineLikePageList(searchDto);
	}


	/**
	 * 좋아요 정보 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DailyRoutineLikeDto selectDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception {
		return dailyRoutineRepository.selectDailyRoutineLike(dto);
	}


	/**
	 * 좋아요 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean insertDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception {
		return dailyRoutineRepository.insertDailyRoutineLike(dto);
	}

	/**
	 * 좋아요 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean deleteDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception {
		return dailyRoutineRepository.deleteDailyRoutineLike(dto) > 0;
	}

	@Transactional
	public void updateDailyRoutinePublicYnBatch(List<Long> planIdxs, String publicYn) throws Exception {
		for(long idx : planIdxs){
			DailyRoutineDto dto = new DailyRoutineDto();
			dto.setIdx(idx);
			dto.setRangeType(RoutineRangeConfigType.valueOf(publicYn).name());
			dailyRoutineRepository.updateDailyRoutinePublicYn(dto);
		}
	}

	@Transactional
	public void deleteDailyRoutineRangeTypeBatch(List<Long> planIdxs) throws Exception {
		for(long idx : planIdxs){
			DailyRoutine dailyRoutine = new DailyRoutine();
			dailyRoutine.addIdx(idx);
			dailyRoutineRepository.delete(dailyRoutine);
		}
	}
}
