package com.routdoo.dailyroutine.module.routine.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name="daily_routine")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutine {
	
	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	@Comment("일련번호")
	private Long idx;
	
	/**
	 * 일상 정보
	 */
	@OneToMany(mappedBy = "dailyRoutine" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<DailyRoutineTimeLine> dailyRoutineTimeLine = new ArrayList<DailyRoutineTimeLine>();  
	
	@Column(length = 20)
	@Comment("일정")
	private String dailyDate;
	
	@Comment("조회수")
	private int view;
	
	@Comment("즐겨찾기")
	@Column(length = 1 , columnDefinition = "char")
	private String favoritYn;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;
	
}
