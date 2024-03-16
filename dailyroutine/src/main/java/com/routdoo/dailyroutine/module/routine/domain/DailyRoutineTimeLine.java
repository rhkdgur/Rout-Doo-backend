package com.routdoo.dailyroutine.module.routine.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDto;
import com.routdoo.dailyroutine.module.routine.service.RoutineWriteType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Entity
@Comment("스케줄 타임라인")
@Table(name="daily_routine_time_line")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutineTimeLine {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("일련번호")
	private Long idx;
	
//	@Comment("작성타입")
//	@Enumerated(EnumType.STRING)
//	private RoutineWriteType writeType;
	
	@Comment("적용일자")
	@Column(length=20)
	private String applyDate;
	
	/**일정 메인 정보*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="dr_idx",nullable = false)
	private DailyRoutine dailyRoutine = new DailyRoutine();
	
	/**place 정보*/
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="place_num",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//	private Place place = new Place();
	
	@Column(length = 300)
	@Comment("제목")
	private String title;
	
	@Column(length = 300)
	@Comment("장소")
	private String placeName;
	
	@Column(length = 300)
	@Comment("주소")
	private String addr;

	@Comment("경도")
	private String mapx;
	
	@Comment("위도")
	private String mapy;
	
	@Comment("순서")
	private int ord;
	
	@Lob
	@Comment("내용")
	private String context;
	
	@Comment("시작시간")
	@Column(length = 2)
	private String shour;
	
	@Comment("시작분")
	@Column(length = 2)
	private String smin;
	
	@Comment("마지막시간")
	@Column(length = 2)
	private String ehour;
	
	@Comment("마지막분")
	@Column(length = 2)
	private String emin;
	
	@Comment("비용")
	private int cost;
	
	@CreatedDate
	@Comment("등록일자")
	private LocalDateTime creatDate;
	
	@LastModifiedDate
	@Comment("수정일자")
	private LocalDateTime modifyDate;

	@Builder
	public DailyRoutineTimeLine(DailyRoutineTimeLineDto dto) {
		if(dto.getIdx() != 0) {
			this.idx = dto.getIdx();
		}
//		this.writeType = RoutineWriteType.valueOf(dto.getWriteType());
		this.applyDate = dto.getApplyDate();
		this.dailyRoutine = new DailyRoutine();
		dailyRoutine.addIdx(dto.getDailyIdx());
//		this.place = new Place();
//		place.addPlaceNum(dto.getPlaceDto().getPlaceNum());
		this.title = dto.getTitle();
		this.placeName = dto.getPlaceName();
		this.addr = dto.getAddr();
		this.mapx = dto.getMapx();
		this.mapy = dto.getMapy();
		this.ord = dto.getOrd();
		this.context = dto.getContext();
		this.shour = dto.getShour();
		this.smin = dto.getSmin();
		this.ehour = dto.getEhour();
		this.emin = dto.getEmin();
		this.cost = dto.getCost();
		this.creatDate = dto.getCreatDate();
		this.modifyDate = dto.getModifyDate();
	}
	
//	public DailyRoutineTimeLine(DailyRoutineTimeLineDto dto, Place place) {
//		if(dto.getIdx() != 0) {
//			this.idx = dto.getIdx();
//		}
//		this.writeType = RoutineWriteType.valueOf(dto.getWriteType());
//		this.applyDate = dto.getApplyDate();
//		this.title = dto.getTitle();
//		this.placeName = dto.getPlaceName();
//		this.ord = dto.getOrd();
//		this.context = dto.getContext();
//		this.shour = dto.getShour();
//		this.smin = dto.getSmin();
//		this.ehour = dto.getEhour();
//		this.emin = dto.getEmin();
//		this.cost = dto.getCost();
//		this.creatDate = dto.getCreatDate();
//		this.modifyDate = dto.getModifyDate();
////		this.place = place;
//	}
	
	public void addDailRoutineTimeLine(DailyRoutineTimeLineDto dto) {
		if(dto.getIdx() != 0) {
			this.idx = dto.getIdx();
		}
//		this.writeType = RoutineWriteType.valueOf(dto.getWriteType());
		this.applyDate = dto.getApplyDate();	
//		this.place.addPlaceNum(dto.getPlaceDto().getPlaceNum());
		this.title = dto.getTitle();
		this.placeName = dto.getPlaceName();
		this.addr = dto.getAddr();
		this.mapx = dto.getMapx();
		this.mapy = dto.getMapy();
		this.ord = dto.getOrd();
		this.context = dto.getContext();
		this.shour = dto.getShour();
		this.smin = dto.getSmin();
		this.ehour = dto.getEhour();
		this.emin = dto.getEmin();
		this.cost = dto.getCost();
		this.creatDate = dto.getCreatDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	public void addDailyRoutine(DailyRoutine dailyRoutine) {
		this.dailyRoutine = dailyRoutine;
	}

}
