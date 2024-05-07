package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineTimeLine;
import com.routdoo.dailyroutine.module.routine.dto.action.timeline.DailyRoutineTimeLineCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.timeline.DailyRoutineTimeLineUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

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

	public DailyRoutineTimeLine toEntity() {
		return DailyRoutineTimeLine.builder().dto(this).build();
	}

	public DailyRoutineTimeLineDto(DailyRoutineTimeLine entity) {
		this.idx = entity.getIdx();
		this.applyDate = entity.getApplyDate();
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
	}

	/**
	 * 등록
	 * @param dailyRoutineTimeLineCreateRequest
	 * @return
	 */
	public static DailyRoutineTimeLineDto createOf(DailyRoutineTimeLineCreateRequest dailyRoutineTimeLineCreateRequest){
		DailyRoutineTimeLineDto request = new DailyRoutineTimeLineDto();
		request.setDailyIdx(dailyRoutineTimeLineCreateRequest.getDailyIdx());
		request.setApplyDate(dailyRoutineTimeLineCreateRequest.getApplyDate());
		request.setTitle(dailyRoutineTimeLineCreateRequest.getTitle());
		request.setPlaceName(dailyRoutineTimeLineCreateRequest.getPlaceName());
		request.setAddr(dailyRoutineTimeLineCreateRequest.getAddr());
		request.setMapx(dailyRoutineTimeLineCreateRequest.getMapx());
		request.setMapy(dailyRoutineTimeLineCreateRequest.getMapy());
		request.setOrd(dailyRoutineTimeLineCreateRequest.getOrd());
		request.setContext(dailyRoutineTimeLineCreateRequest.getContext());
		request.setShour(dailyRoutineTimeLineCreateRequest.getShour());
		request.setSmin(dailyRoutineTimeLineCreateRequest.getSmin());
		request.setEhour(dailyRoutineTimeLineCreateRequest.getEhour());
		request.setEmin(dailyRoutineTimeLineCreateRequest.getEmin());
		request.setCost(dailyRoutineTimeLineCreateRequest.getCost());
		return request;
	}

	/**
	 * 수정
	 * @param dailyRoutineTimeLineUpdateRequest
	 * @return
	 */
	public static DailyRoutineTimeLineDto updateOf(DailyRoutineTimeLineUpdateRequest dailyRoutineTimeLineUpdateRequest){
		DailyRoutineTimeLineDto request = new DailyRoutineTimeLineDto();
		request.setIdx(dailyRoutineTimeLineUpdateRequest.getIdx());
		request.setDailyIdx(dailyRoutineTimeLineUpdateRequest.getDailyIdx());
		request.setApplyDate(dailyRoutineTimeLineUpdateRequest.getApplyDate());
		request.setTitle(dailyRoutineTimeLineUpdateRequest.getTitle());
		request.setPlaceName(dailyRoutineTimeLineUpdateRequest.getPlaceName());
		request.setAddr(dailyRoutineTimeLineUpdateRequest.getAddr());
		request.setMapx(dailyRoutineTimeLineUpdateRequest.getMapx());
		request.setMapy(dailyRoutineTimeLineUpdateRequest.getMapy());
		request.setOrd(dailyRoutineTimeLineUpdateRequest.getOrd());
		request.setContext(dailyRoutineTimeLineUpdateRequest.getContext());
		request.setShour(dailyRoutineTimeLineUpdateRequest.getShour());
		request.setSmin(dailyRoutineTimeLineUpdateRequest.getSmin());
		request.setEhour(dailyRoutineTimeLineUpdateRequest.getEhour());
		request.setEmin(dailyRoutineTimeLineUpdateRequest.getEmin());
		request.setCost(dailyRoutineTimeLineUpdateRequest.getCost());
		return request;
	}

	public Map<String,Object> toMap(){
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("idx", this.idx);
		map.put("dailyIdx", this.dailyIdx);
		map.put("title", this.title);
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
