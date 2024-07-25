package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineInvite;
import com.routdoo.dailyroutine.module.routine.dto.action.invite.DailyRoutineInviteCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@Schema(description = "일정 공유 회원 DTO")
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineInviteDto {

	/**일련번호*/
	@Schema(description = "일련번호")
	private Long idx = 0L;
	
	/**스케줄 일련번호*/
	@Schema(description = "일정 일련번호")
	private Long dailyIdx = 0L;
	
	/**회원 일련번호*/
	@Schema(description = "회원 아이디")
	private String memberId = "";

	/**등록일자*/
	@Schema(description = "등록일자")
	private LocalDateTime createDate;
	
	/**수정일자*/
	@Schema(description = "수정일자")
	private LocalDateTime modifyDate;

	/**친구 초대 목록*/
	private List<String> memberIds = new ArrayList<>();
	
	public DailyRoutineInvite toEntity() {
		return DailyRoutineInvite.builder().dto(this).build();
	}

	public DailyRoutineInviteDto(DailyRoutineInvite entity){
		this.idx = entity.getIdx();
		this.dailyIdx = entity.getDailyRoutine().getIdx();
		this.memberId = entity.getMember().getId();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}

	public static DailyRoutineInviteDto createOf(DailyRoutineInviteCreateRequest dailyRoutineInviteCreateRequest){
		DailyRoutineInviteDto request = new DailyRoutineInviteDto();
		request.setDailyIdx(dailyRoutineInviteCreateRequest.getDailyIdx());
		int len = dailyRoutineInviteCreateRequest.getMemberId().split(",").length;
		if( len > 1){
			for(int i = 0; i<len; i++){
				request.getMemberIds().add(dailyRoutineInviteCreateRequest.getMemberId().split(",")[i]);
			}
		}else {
			request.setMemberId(dailyRoutineInviteCreateRequest.getMemberId());
		}
		return request;
	}
	
}
