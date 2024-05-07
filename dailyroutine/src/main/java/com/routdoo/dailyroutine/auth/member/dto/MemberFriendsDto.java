package com.routdoo.dailyroutine.auth.member.dto;

import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;
import com.routdoo.dailyroutine.auth.member.dto.action.friend.MemberFriendsBlockCreateRequest;
import com.routdoo.dailyroutine.auth.member.dto.action.friend.MemberFriendsCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MemberFriendsDto implements Serializable{

	private static final long serialVersionUID = 1L;

	/**일련번호*/
	@Schema(description = "일련번호")
	private Long idx = 0L;

	/**회원아이디*/
	@Schema(description = "회원아이디")
	private String memberId = "";
	
	/**차단여부 */
	@Schema(description = "차단여부", example = "Y(차단),N(차단안함))")
	private String blockYn = "";
		
	/**등록일자*/
	@Schema(description = "등록일자")
	private LocalDateTime createDate;
	
	/**수정일*/
	@Schema(description = "수정일자")
	private LocalDateTime modifyDate;

	@Schema(description = "회원정보(조회에 사용)")
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

	/**
	 * 등록
	 * @param memberFriendsCreateRequest
	 * @return
	 */
	public static MemberFriendsDto createOf(MemberFriendsCreateRequest memberFriendsCreateRequest){
		MemberFriendsDto dto = new MemberFriendsDto();
		dto.setMemberId(memberFriendsCreateRequest.getMemberId());
		dto.setBlockYn(memberFriendsCreateRequest.getBlockYn());
		return dto;
	}

	/**
	 * 친구 차단
	 * @param memberFriendsBlockCreateRequest
	 * @return
	 */
	public static MemberFriendsDto blockOf(MemberFriendsBlockCreateRequest memberFriendsBlockCreateRequest){
		MemberFriendsDto dto = new MemberFriendsDto();
		dto.setIdx(memberFriendsBlockCreateRequest.getIdx());
		dto.setBlockYn(memberFriendsBlockCreateRequest.getBlockYn());
		return dto;
	}

}
