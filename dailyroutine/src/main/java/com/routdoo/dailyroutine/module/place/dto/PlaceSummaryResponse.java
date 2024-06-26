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

    /**카테고리 코드*/
    private String categCd;

    /***카테고리 명*/
    private String categNm;

    /**주소*/
    private String addr;
    
    /**상세 주소*/
    private String addrDetail;

    /**경도*/
    private String mapx;

    /**위도*/
    private String mapy;

    /**사용여부*/
    private String pstatus;

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
    private LocalDateTime modifyDate;

}
