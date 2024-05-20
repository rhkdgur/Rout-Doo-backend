package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;
import com.routdoo.dailyroutine.module.routine.dto.action.DailyRoutineChangeRangeTypeRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.DailyRoutineCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.DailyRoutineUpdateRequest;
import com.routdoo.dailyroutine.module.routine.service.RoutineRangeConfigType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.domain
* @fileName      : DailyRoutine.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.17
* @description   : 일정 대표 entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.17        ghgo       최초 생성
 */
@Schema(description = "일정 기본정보 DTO")
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineDto {

	@Schema(description = "일정 일련번호")
	private Long idx = 0L;
	
	/**제목*/
	@Schema(description = "제목")
	private String title ="";
	
	/**태그*/
	@Schema(description = "태그")
	private String tag = "";
	
	/**시작일자*/
	@Schema(description = "시작일자")
	private String startDate = "";
	
	/**마지막일자*/
	@Schema(description = "마지막일자")
	private String endDate = "";
	
	/**일정타입*/
	@Schema(description = "일정 타입", example = "DAY : 단기, LONG_DAY : 장기")
	private String dayType = "";
	
	/**공개여부*/
	@Schema(description = "공개범위" ,example = "PUBLIC : 공개 , PRIVATE : 비공개")
	private String rangeType = "";
	
	/**등록일자*/
	@Schema(description = "등록일자")
	private LocalDateTime createDate;

	/**수정일자*/
	@Schema(description = "수정일자")
	private LocalDateTime modifyDate;
	
	/**회원 아이디*/
	@Schema(description = "회원아이디")
	private String memberId = "";
	
	/**이름*/
	@Schema(description = "닉네임")
	private String nickname = "";

	@Schema(description = "좋아요 여부")
	private String likeYn = "";

	private long likeIdx = 0;

	@Schema(description = "타임라인 리스트")
	private List<DailyRoutineTimeLineDto> timeList = new ArrayList<DailyRoutineTimeLineDto>();
	
	public DailyRoutine toEntity() {
		return DailyRoutine.builder().dto(this).build();
	}

	public DailyRoutineDto(DailyRoutine entity) {
		this.idx = entity.getIdx();
		this.memberId = entity.getMember().getId();
		this.tag = entity.getTag();
		this.title = entity.getTitle();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.dayType = entity.getDayType().name();
		this.rangeType = entity.getRangeType().name();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
		if(entity.getMember() != null){
			if(!entity.getMember().getNickname().isEmpty()){
				this.nickname = entity.getMember().getNickname();
			}
		}
	}

	/**
	 * 등록 처리
	 * @param dailyRoutineCreateRequest
	 * @return
	 */
	public static DailyRoutineDto createOf(DailyRoutineCreateRequest dailyRoutineCreateRequest){
		DailyRoutineDto dto = new DailyRoutineDto();
		dto.setTitle(dailyRoutineCreateRequest.getTitle());
		dto.setTag(dailyRoutineCreateRequest.getTag());
		dto.setStartDate(dailyRoutineCreateRequest.getStartDate());
		dto.setEndDate(dailyRoutineCreateRequest.getEndDate());
		dto.setDayType(dailyRoutineCreateRequest.getDayType());
		dto.setRangeType(dailyRoutineCreateRequest.getRangeType());
		return dto;
	}

	/**
	 * 수정
	 * @param dailyRoutineUpdateRequest
	 * @return
	 */
	public static DailyRoutineDto updateOf(DailyRoutineUpdateRequest dailyRoutineUpdateRequest){
		DailyRoutineDto dto = new DailyRoutineDto();
		dto.setIdx(dailyRoutineUpdateRequest.getIdx());
		dto.setTitle(dailyRoutineUpdateRequest.getTitle());
		dto.setTag(dailyRoutineUpdateRequest.getTag());
		dto.setStartDate(dailyRoutineUpdateRequest.getStartDate());
		dto.setEndDate(dailyRoutineUpdateRequest.getEndDate());
		dto.setDayType(dailyRoutineUpdateRequest.getDayType());
		dto.setRangeType(dailyRoutineUpdateRequest.getRangeType());
		return dto;
	}

	/**
	 * 공개여부 변경 메소드
	 * @param dailyRoutineChangeRangeTypeRequest
	 * @return
	 */
	public static DailyRoutineDto changeRangeTypeOf(DailyRoutineChangeRangeTypeRequest dailyRoutineChangeRangeTypeRequest){
		DailyRoutineDto dto = new DailyRoutineDto();
		dto.setIdx(dailyRoutineChangeRangeTypeRequest.getIdx());
		dto.setRangeType(dailyRoutineChangeRangeTypeRequest.getRangeType());
		return dto;
	}

	public String getRangeTypeDisplay(){
		return RoutineRangeConfigType.valueOf(this.rangeType).getDisplay();
	}

	// 일자별 타임라인 map 처리
	//현재는 사용안함.... 분리를 해버림..
	public Map<String,Object> getGroupTimeMap() {
		Map<String,Object> groupMap = new LinkedHashMap<>();

		if(!this.timeList.isEmpty()){
			//중복일자 제거
			List<String> dateList = new ArrayList<>();
			for(DailyRoutineTimeLineDto time : this.timeList){
				dateList.add(time.getApplyDate());
			}

			dateList = dateList.stream().distinct().toList();

			Map<String, List<Map<String,Object>>> map = this.timeList.stream().map(DailyRoutineTimeLineDto::toMap).collect(Collectors.groupingBy(p-> p.get("applyDate").toString()));
			groupMap.put("days", dateList);
			groupMap.put("daysMap",map);
		}

		return groupMap;
	}
}
