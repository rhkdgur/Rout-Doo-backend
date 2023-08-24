package com.routdoo.dailyroutine.module.routine.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.service.RoutineDayType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Entity
@Comment("스케줄")
@Table(name="daily_routine")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutine{
	
	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	@Comment("일련번호")
	private Long idx;
	
	/**
	 * 일상 정보
	 */
	@OneToMany(mappedBy = "dailyRoutine",orphanRemoval=true ,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.LAZY)
	private List<DailyRoutineTimeLine> dailyRoutineTimeLine = new ArrayList<DailyRoutineTimeLine>();  
	
	@Column(length = 20)
	@Comment("시작일자")
	private String startDate;
	
	@Column(length = 20)
	@Comment("시작일자")
	private String endDate;
	
	@Comment("일정타입")
	@Enumerated(EnumType.STRING)
	private RoutineDayType dayType;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;

	@Builder
	public DailyRoutine(DailyRoutineDto dto) {
		if(dto.getIdx() != 0) {
			this.idx = dto.getIdx();
		}
		this.startDate = dto.getStartDate();
		this.endDate = dto.getEndDate();
		this.dayType = RoutineDayType.valueOf(dto.getDayType());
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	public void addIdx(Long idx) {
		this.idx = idx;
	}
	
	public void addDailyRoutineTimeLine(DailyRoutineTimeLine timeLine) {
		this.getDailyRoutineTimeLine().add(timeLine);
		timeLine.addDailyRoutine(this);
	}

}
