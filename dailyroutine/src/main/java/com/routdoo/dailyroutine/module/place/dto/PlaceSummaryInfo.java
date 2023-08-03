package com.routdoo.dailyroutine.module.place.dto;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.dto
* @fileName      : PlaceSummaryInfo.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.03
* @description   : 장소 요약 정보 
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.03        ghgo       최초 생성
 */
public interface PlaceSummaryInfo {

	/**일련번호*/
	String getPlaceNum();
	
	/**제목*/
	String getTitle();
	
	/**경도*/
	String getMapx();
	
	/**위도*/
	String getMapy();
	
	/**좋아요수*/
	int getLikeCnt();
	
	/**댓글 개수*/
	int getCommentCnt();
	
}
