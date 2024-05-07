package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceCommentResponse
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 장소 댓글 response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCommentResponse {

    /**일련번호*/
    private Long idx = 0L;

    /**회원 정보*/
    private MemberSummaryResponse memberSummaryResponse;

    /**장소 일련번호*/
    private String placeNum;

    /**내용*/
    private String content;

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
    private LocalDateTime modifyDate;

    /**
     * 장소 정보 및 댓글 목록
     * @param commentDto
     * @return
     */
    public static PlaceCommentResponse of(PlaceCommentDto commentDto){
        PlaceCommentResponse response = new PlaceCommentResponse();
        response.setIdx(commentDto.getIdx());
        response.setPlaceNum(commentDto.getPlaceNum());
        response.setContent(commentDto.getContent());
        response.setCreateDate(commentDto.getCreateDate());
        response.setModifyDate(commentDto.getModifyDate());
        response.setMemberSummaryResponse(MemberSummaryResponse.dtoResponseOf(commentDto.getMemberDto()));
        return response;
    }

}
