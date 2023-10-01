package com.routdoo.dailyroutine.auth.member.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.auth.member.dto.FriendListDto;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;

import jakarta.persistence.Column;
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

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member.domain
* @fileName      : FriendList.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.06
* @description   : 친구목 entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.06        ghgo       최초 생성
 */
@Entity
@Table(name="friend_list")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FriendList {
	
	
	@Comment("일련번호")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;

	@ManyToOne(optional = false,fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member = new Member();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="daily_idx")
	private DailyRoutine dailyRoutine = new DailyRoutine();
	
	@Comment("차단여부")
	@Column(length=1,columnDefinition = "char")
	private String blockYn = "";
	
	/**등록일자*/
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	/**수정일*/
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;
	
	@Builder
	public FriendList(FriendListDto dto){
		if(dto.getIdx() != 0) {
			this.idx = dto.getIdx();
		}
		this.member.addId(dto.getMemberId());
		this.dailyRoutine.addIdx(dto.getDailyIdx());
		this.blockYn = dto.getBlockYn();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	public void addIdx(Long idx) {
		this.idx = idx;
	}
}
