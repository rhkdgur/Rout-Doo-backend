package com.routdoo.dailyroutine.module.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceSummaryResponse
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 장소 summary response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceSummaryResponse {

    /**일련번호*/
    private String placeNum;

    /**제목*/
    private String title;

    /**연락처*/
    private String tel;

    /**카테고리 코드*/
    private String categCd;

    /***카테고리 명*/
    private String categNm;

    /**주소*/
    private String addr;

    /**경도*/
    private String mapx;

    /**위도*/
    private String mapy;

    /**이용안내*/
    private String useInfo;

    /**상세정보*/
    private String detailText;

    /**사용여부*/
    private String pstatus;

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
    private LocalDateTime modifyDate;

}
