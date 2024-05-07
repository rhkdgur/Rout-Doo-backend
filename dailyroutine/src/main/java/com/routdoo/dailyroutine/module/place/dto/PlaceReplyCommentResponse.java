package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceReplyCommentResponse
 * author         : GAMJA
 * date           : 2024/05/06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceReplyCommentResponse {

    /**일련번호*/
    private Long idx = 0L;

    /**코멘트 일련번호*/
    private Long commentIdx = 0L;

    /**장소번호*/
    private String placeNum = "";

    /**내용*/
    private String content = "";

    /**회원 정보*/
    private MemberSummaryResponse memberSummaryResponse;

    /**자신의 글인지 확인 용*/
    private boolean isUser = false;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public PlaceReplyCommentResponse(
            long idx,
            long commentIdx,
            String placeNum,
            String content,
            String memberId,
            String nickname,
            LocalDateTime creatDate,
            LocalDateTime modifyDate
    ){
        this.idx = idx;
        this.commentIdx = commentIdx;
        this.placeNum = placeNum;
        this.content = content;
        this.memberSummaryResponse.setId(memberId);
        this.memberSummaryResponse.setNickname(nickname);
        this.createDate = creatDate;
        this.modifyDate = modifyDate;
    }

    public void checkIsUser(String memberId) {
        this.isUser = this.memberSummaryResponse.getId().equals(memberId);
    }

}
