package com.routdoo.dailyroutine.module.routine.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;
import com.routdoo.dailyroutine.module.routine.service.RoutineDayType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineDto {
	
	private Long idx = 0L;
	
	/**시작일자*/
	private String startDate;
	
	/**마지막일자*/
	private String endDate;
	
	/**일정타입*/
	private String dayType;
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;
	
	List<DailyRoutineTimeLineDto> timeList = new ArrayList<DailyRoutineTimeLineDto>();
	
	public DailyRoutine toEntity() {
		return DailyRoutine.builder().dto(this).build();
	}

	public DailyRoutineDto(DailyRoutine entity) {
		this.idx = entity.getIdx();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.dayType = entity.getDayType().name();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}  

}
