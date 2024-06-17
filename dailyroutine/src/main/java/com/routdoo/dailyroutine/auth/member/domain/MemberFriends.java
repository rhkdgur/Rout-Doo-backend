package com.routdoo.dailyroutine.auth.member.domain;

import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member.domain
* @fileName      : FriendList.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.06
* @description   : 친구목록 entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.06        ghgo       최초 생성
 */
@Entity
@Table(name="member_friends")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MemberFriends {
	
	
	@Comment("일련번호")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;

	@ManyToOne(optional = false,fetch = FetchType.EAGER)
	@JoinColumn(name="member_id")
	private Member member;

	@Comment("초대된 아이디")
	private String invitedId;
	
	@Comment("차단여부")
	@Column(length = 1 , columnDefinition = "char default 'N'")
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
	public MemberFriends(MemberFriendsDto dto){
		if(dto.getIdx() != 0) {
			this.idx = dto.getIdx();
		}
		this.member = new Member();
		this.member.addId(dto.getMemberId());
		this.invitedId = dto.getInvitedId();
		this.blockYn = dto.getBlockYn();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	public void addIdx(Long idx) {
		this.idx = idx;
	}
}
