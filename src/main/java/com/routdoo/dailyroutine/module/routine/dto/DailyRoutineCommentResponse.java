package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineCommentResponse
 * author         : rhkdg
 * date           : 2024-05-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-02        rhkdg       최초 생성
 */
@Getter
@Setter
public class DailyRoutineCommentResponse {

    private long idx = 0L;

    private long dailyIdx = 0L;

    private String memberId = "";

    private String content = "";

    /**답글 개수*/
    private int replyCnt = 0;

    private String enableType = "";

    private boolean isUser = false;

    private LocalDateTime createDate;

    /**회원 dto*/
    private MemberSummaryResponse memberSummaryResponse = new MemberSummaryResponse();

    public DailyRoutineCommentResponse(long idx, String memberId, long dailyIdx, String nickname, String content, String mbti,
                                  long replyCnt, Timestamp createDate, String enableType){
        this.idx = idx;
        this.memberId = memberId;
        this.dailyIdx = dailyIdx;
        this.memberSummaryResponse.setNickname(nickname);
        this.content = content;
        this.memberSummaryResponse.setMbti(mbti);
        this.replyCnt = (int) replyCnt;
        this.createDate = createDate.toLocalDateTime();
        this.enableType = enableType;
    }

    public void addIsUser(String memberId){
        this.isUser = this.memberId.equals(memberId);
    }

}
