package com.routdoo.dailyroutine.module.routine.dto;

import java.time.LocalDateTime;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineInvite;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.dto
* @fileName      : DailyRoutineInviteDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.09.26
* @description   : 멤버 초대 목록 Dto
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.09.26        ghgo       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineInviteDto {

	/**일련번호*/
	private Long idx = 0L;
	
	/**스케줄 일련번호*/
	private Long dailyIdx = 0L;
	
	/**회원 일련번호*/
	private String memberId = "";

	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;
	
	/**회원 정보*/
	private MemberDto member = new MemberDto();
	
	public DailyRoutineInvite toEntity() {
		return DailyRoutineInvite.builder().dto(this).build();
	}
	
}
