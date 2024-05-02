package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineInviteResponse
 * author         : rhkdg
 * date           : 2024-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-01        rhkdg       최초 생성
 */
@Getter
@Setter
public class DailyRoutineInviteResponse {

    /**
     * 일련번호
     */
    private Long idx = 0L;

    /**
     * 스케줄 일련번호
     */
    private Long dailyIdx = 0L;

    /**
     * 회원 요약 정보
     */
    private MemberSummaryResponse memberSummaryResponse = new MemberSummaryResponse();

    /**
     * 등록일자
     */
    private LocalDateTime createDate;

    /**
     * 수정일자
     */
    private LocalDateTime modifyDate;

    public DailyRoutineInviteResponse(Long idx, Long dailyIdx,
                                      String memberId, String nickname, String gender,
                                      LocalDateTime createDate, LocalDateTime modifyDate) {
        this.idx = idx;
        this.dailyIdx = dailyIdx;
        this.memberSummaryResponse.setId(memberId);
        this.memberSummaryResponse.setNickname(nickname);
        this.memberSummaryResponse.setGender(gender);
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
