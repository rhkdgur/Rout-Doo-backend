package com.routdoo.dailyroutine.module.routine.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="daily_routine_invite")
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutineInvite {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("일련번호")
	private Long idx;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="daily_idx")
	private DailyRoutine dailyRoutine = new DailyRoutine();
	
	@ManyToOne(fetch = FetchType.EAGER)
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

	@Builder(builderMethodName = "createInvite")
	public DailyRoutineInvite(DailyRoutineInviteCreateRequest dailyRoutineInviteCreateRequest){
		this.dailyRoutine.addIdx(dailyRoutineInviteCreateRequest.getDailyIdx());
		this.member.addId(dailyRoutineInviteCreateRequest.getMemberId());
	}
	
	public void addDailyRoutineAndMember(DailyRoutine dailyRoutine, Member member) {
		this.dailyRoutine = dailyRoutine;
		this.member = member;
	}
}
