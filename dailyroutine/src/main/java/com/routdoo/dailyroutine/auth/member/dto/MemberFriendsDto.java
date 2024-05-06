package com.routdoo.dailyroutine.auth.member.dto;

import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
	@NotBlank
	private String memberId = "";
	
	/**차단여부 */
	@Schema(description = "차단여부", example = "Y(차단),N(차단안함))")
	@NotEmpty
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

}
