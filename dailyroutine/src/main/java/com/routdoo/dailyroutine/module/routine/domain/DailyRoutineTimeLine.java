package com.routdoo.dailyroutine.module.routine.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name="daily_routine_time_line")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutineTimeLine {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("일련번호")
	private Long idx;
	
	/**일정 메인 정보*/
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="idx")
	private DailyRoutine dailyRoutine = new DailyRoutine();
	
	@Column(length = 300)
	@Comment("제목")
	private String title;
	
	@Comment("순서")
	private int ord;
	
	@Lob
	@Comment("내용")
	private String context;
	
	@Comment("시간")
	@Column(length = 2)
	private String hour;
	
	@Comment("분")
	@Column(length = 2)
	private String minute;
	
	@CreatedDate
	@Comment("등록일자")
	private LocalDateTime creatDate;
	
	@LastModifiedDate
	@Comment("수정일자")
	private LocalDateTime modifyDate;
	
}
