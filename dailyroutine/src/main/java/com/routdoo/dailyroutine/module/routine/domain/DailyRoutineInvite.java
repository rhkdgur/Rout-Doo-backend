package com.routdoo.dailyroutine.module.routine.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="daily_routine_invite")
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutineInvite {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("일련번호")
	private Long idx;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="daily_idx")
	private DailyRoutine dailyRoutine = new DailyRoutine();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member = new Member();
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;

	@Builder
	public DailyRoutineInvite(DailyRoutineInviteDto dto) {
		if(dto.getIdx() != 0) {
			this.idx = dto.getIdx();
		}
		this.dailyRoutine.addIdx(dto.getDailyIdx());
		this.member.addId(dto.getMemberId());
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	public void addDailyRoutineAndMember(DailyRoutine dailyRoutine, Member member) {
		this.dailyRoutine = dailyRoutine;
		this.member = member;
	}
}
