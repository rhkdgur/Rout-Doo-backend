package com.routdoo.dailyroutine.auth.member.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberFriendsDto implements Serializable{

	private static final long serialVersionUID = 1L;

	/**일련번호*/
	private Long idx = 0L;
	
	private Long dailyIdx = 0L;

	/**회원아이디*/
	private String memberId = "";
	
	/**차단여부 */
	private String blockYn = "";
		
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일*/
	private LocalDateTime modifyDate;
	
	private MemberDto memberDto = new MemberDto();
	
	public MemberFriends toEntity() {
		return MemberFriends.builder().dto(this).build();
	}
	
	public MemberFriendsDto(MemberFriends entity) {
		this.idx = entity.getIdx();
		this.memberId = entity.getMember().getId();
		this.blockYn = entity.getBlockYn();
		this.memberDto = new MemberDto(entity.getMember());
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}

}
