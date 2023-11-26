package com.routdoo.dailyroutine.module.place.dto;

import java.time.LocalDateTime;

import com.routdoo.dailyroutine.module.place.domain.Place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.dto
* @fileName      : PlaceDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.27
* @description   : 장소 dto
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.27        ghgo       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaceDto {

	/**일련번호*/
	private String placeNum;

	/**제목*/
	private String title;
	
	/**해쉬태그*/
	private String hashtag;
	
	/**주소*/
	private String addr;
	
	/**경도*/
	private String mapx;
	
	/**위도*/
	private String mapy;
	
	/**소개글*/
	private String introText;
	
	/**이용안내*/
	private String useInfo;
	
	/**상세정보*/
	private String detailText;
	
	/**댓글수*/
	private int likeCnt = 0;
	
	/**좋아요 수*/
	private int commentCnt = 0;
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;

	/**회원 아이디*/
	private String memberId = "";
	
	public Place toEntity() {
		return Place.builder().dto(this).build();
	}

	public void addPlaceSummaryInfo(Place entity) {
		this.placeNum = entity.getPlaceNum();
		this.title = entity.getTitle();
		this.memberId = entity.getMember().getId();
		this.hashtag = entity.getHashtag();
		this.addr = entity.getAddr();
		this.mapx = entity.getMapx();
		this.mapy = entity.getMapy();
		this.introText = entity.getIntroText();
		this.useInfo = entity.getUseInfo();
		this.detailText = entity.getDetailText();
		this.likeCnt = entity.getPlaceLikes().size();
		this.commentCnt = entity.getPlaceComments().size();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}
	
}
