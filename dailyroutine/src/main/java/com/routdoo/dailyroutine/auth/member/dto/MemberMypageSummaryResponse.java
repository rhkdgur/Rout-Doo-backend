package com.routdoo.dailyroutine.auth.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto
 * fileName       : MemberMypageSummaryResponse
 * author         : GAMJA
 * date           : 2024/05/05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/05        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberMypageSummaryResponse {

    /**공개일정*/
    private long publicCnt = 0L;

    /**친구 수*/
    private long friendCnt = 0L;

    /**나만의 장소 수*/
    private long placeCnt = 0L;

}
