package com.routdoo.dailyroutine.module.routine.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineTimeLine;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.domain
* @fileName      : DailyRoutineTimeLine.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.17
* @description   : 일정 시간 별 정보 entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.17        ghgo       최초 생성
 */
@Schema(description = "일정 타임라인 DTO")
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineTimeLineDto {
	
	/**일련번호*/
	@Schema(description = "일련번호")
	private Long idx = 0L;
	
	/**일정 메인 정보*/
	@Schema(description = "잃정 일련번호")
	private Long dailyIdx = 0L;
	
	/**작성타입*/
	@Schema(description = "작성타입", example = "직접입력 : DIRECT, 검색입력 : SEARCH ")
	private String writeType;
	
	/**적용일자*/
	@Schema(description = "적용일자")
	private String applyDate;
	
	/**제목*/
	@Schema(description = "제목")
	private String title;
	
	/**장소명*/
	@Schema(description = "장소명")
	private String placeName;

	/**주소*/
	@Schema(description = "주소")
	private String addr;
	
	/**경도*/
	@Schema(description = "경도")
	private String mapx;
	
	/**위도*/
	@Schema(description = "위도")
	private String mapy;

	/**순서*/
	@Schema(description = "순서")
	private int ord = 0;
	
	/**내용*/
	@Schema(description = "내용")
	private String context;
	
	/**시작시간*/
	@Schema(description = "시작시간" , example = "01")
	private String shour;
	
	/**시작분*/
	@Schema(description = "시작분", example = "01")
	private String smin;
	
	/**마지막시간*/
	@Schema(description = "마지막시간", example = "01")
	private String ehour;
	
	/**마지막분*/
	@Schema(description = "마지막 분", example = "01")
	private String emin;
	
	/**비용*/
	@Schema(description = "비용", defaultValue = "0",example = "1000")
	private int cost = 0;
	
	/**등록일자*/
	@Schema(description = "등록일자")
	private LocalDateTime creatDate;
	
	/**수정일자*/
	@Schema(description = "수정일자")
	private LocalDateTime modifyDate;
	
	/**place 정보*/
//	private PlaceDto placeDto = new PlaceDto();
	
	public DailyRoutineTimeLine toEntity() {
		return DailyRoutineTimeLine.builder().dto(this).build();
	}

	public DailyRoutineTimeLineDto(DailyRoutineTimeLine entity) {
		this.idx = entity.getIdx();
		this.writeType = entity.getWriteType().name();
		this.title = entity.getTitle();
		this.placeName = entity.getPlaceName();
		this.ord = entity.getOrd();
		this.context = entity.getContext();
		this.shour = entity.getShour();
		this.smin = entity.getSmin();
		this.ehour = entity.getEhour();
		this.emin = entity.getEmin();
		this.cost = entity.getCost();
		this.creatDate = entity.getCreatDate();
		this.modifyDate = entity.getModifyDate();
		this.dailyIdx = entity.getDailyRoutine().getIdx();
//		this.placeDto = new PlaceDto();
//		placeDto.addPlaceSummaryInfo(entity.getPlace());
	}

	/**
	 * dto -> summary map
	 * @return
	 */
	public Map<String,Object> toSummaryMap(){
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("idx", this.idx);
		map.put("dailyIdx", this.dailyIdx);
		map.put("title", this.title);
		map.put("startTime", this.shour+":"+this.smin);
		map.put("endTime", this.ehour+":"+this.emin);
		map.put("ord", this.ord);
		return map;
	}
	
}
