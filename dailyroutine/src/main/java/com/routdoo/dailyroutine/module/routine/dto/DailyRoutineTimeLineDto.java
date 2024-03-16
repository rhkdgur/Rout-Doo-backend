package com.routdoo.dailyroutine.module.routine.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.routdoo.dailyroutine.common.exception.validate.annotation.date.Date;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineTimeLine;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	@NotBlank
	private Long dailyIdx = 0L;
	
	/**작성타입*/
//	@Schema(description = "작성타입", example = "직접입력 : DIRECT, 검색입력 : SEARCH ")
//	@NotBlank
//	private String writeType;
	
	/**적용일자*/
	@Schema(description = "적용일자")
	@NotBlank
	@Date
	private String applyDate;
	
	/**제목*/
	@Schema(description = "제목")
	@NotBlank
	private String title;
	
	/**장소명*/
	@Schema(description = "장소명")
	private String placeName;

	/**주소*/
	@Schema(description = "주소")
	@NotBlank
	private String addr;
	
	/**경도*/
	@Schema(description = "경도")
	@NotBlank
	private String mapx;
	
	/**위도*/
	@Schema(description = "위도")
	@NotBlank
	private String mapy;

	/**순서*/
	@Schema(description = "순서")
	private int ord = 0;
	
	/**내용*/
	@Schema(description = "내용")
	@NotEmpty
	private String context;
	
	/**시작시간*/
	@Schema(description = "시작시간" , example = "01")
	@NotNull
	private String shour;
	
	/**시작분*/
	@Schema(description = "시작분", example = "01")
	@NotNull
	private String smin;
	
	/**마지막시간*/
	@Schema(description = "마지막시간", example = "01")
	@NotNull
	private String ehour;
	
	/**마지막분*/
	@Schema(description = "마지막 분", example = "01")
	@NotNull
	private String emin;
	
	/**비용*/
	@Schema(description = "비용", defaultValue = "0",example = "1000")
	@NotBlank
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
		this.applyDate = entity.getApplyDate();
//		this.writeType = entity.getWriteType().name();
		this.title = entity.getTitle();
		this.placeName = entity.getPlaceName();
		this.addr = entity.getAddr();
		this.mapx = entity.getMapx();
		this.mapy = entity.getMapy();
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
		map.put("applyDate",this.applyDate);
		map.put("startTime", this.shour+":"+this.smin);
		map.put("endTime", this.ehour+":"+this.emin);
		map.put("ord", this.ord);
		return map;
	}

	public Map<String,Object> toMap(){
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("idx", this.idx);
		map.put("dailyIdx", this.dailyIdx);
		map.put("title", this.title);
//		map.put("writeType", this.writeType);
		map.put("applyDate",this.applyDate);
		map.put("startTime", this.shour+":"+this.smin);
		map.put("endTime", this.ehour+":"+this.emin);
		map.put("addr",this.addr);
		map.put("mapx",this.mapx);
		map.put("mapy",this.mapy);
		map.put("ord", this.ord);
		map.put("placeName",this.placeName);
		map.put("context",this.context);
		map.put("cost",this.cost);
		map.put("createDate",this.creatDate);
		map.put("modifyDate",this.modifyDate);
		return map;
	}
	
}
