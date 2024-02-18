package com.routdoo.dailyroutine.module.routine.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.service.RoutineDayType;
import com.routdoo.dailyroutine.module.routine.service.RoutineRangeConfigType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
	
	@Column(length = 300)
	@Comment("큰 제목")
	/**큰 제목*/
	private String title;

	@Comment("태그")
	private String tag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id",nullable = false)
	private Member member;
	
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

	@Comment("공개여부")
	@Enumerated(EnumType.STRING)
	private RoutineRangeConfigType rangeType;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;

	@Builder
	public DailyRoutine(DailyRoutineDto dto) {
		if (dto.getIdx() != 0) {
			this.idx = dto.getIdx();
		}
		this.member.addId(dto.getMemberId());
		this.title = dto.getTitle();
		this.tag = dto.getTag();
		this.startDate = dto.getStartDate();
		//당일일 경우
		if(dto.getDayType().equals(RoutineDayType.DAY.name())){
			this.endDate = dto.getStartDate();
		}else {
			this.endDate = dto.getEndDate();
		}
		this.dayType = RoutineDayType.valueOf(dto.getDayType());
		this.rangeType = RoutineRangeConfigType.valueOf(dto.getRangeType());
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	public void addIdx(Long idx) {
		this.idx = idx;
	}
	
	public void addMember(Member member) {
		this.member = member;
	}
	
	public void addDailyRoutineTimeLine(DailyRoutineTimeLine timeLine) {
		this.getDailyRoutineTimeLine().add(timeLine);
		timeLine.addDailyRoutine(this);
	}

}
