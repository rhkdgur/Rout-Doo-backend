package com.routdoo.dailyroutine.module.routine.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;

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
	
	/**제목*/
	private String title ="";
	
	/**태그*/
	private String tag = "";
	
	/**시작일자*/
	private String startDate = "";
	
	/**마지막일자*/
	private String endDate = "";
	
	/**일정타입*/
	private String dayType = "";
	
	/**공개여부*/
	private String rangeType = "";
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;
	
	/**회원 아이디*/
	private String memberId = "";
	
	List<DailyRoutineTimeLineDto> timeList = new ArrayList<DailyRoutineTimeLineDto>();
	
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
	}

	/**
	 * dto -> summary map
	 * @return
	 */
	public Map<String,Object> toSummaryMap (){
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("idx",this.idx);
		map.put("memberId", this.memberId);
		map.put("tag",this.tag);
		map.put("title",this.title);
		map.put("startDate",this.startDate);
		map.put("endDate",this.endDate);
		map.put("rangeType",this.rangeType);
		return map;
	}

}
