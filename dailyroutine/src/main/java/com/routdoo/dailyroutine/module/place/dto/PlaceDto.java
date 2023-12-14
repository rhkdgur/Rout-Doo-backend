package com.routdoo.dailyroutine.module.place.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.Place;

import com.routdoo.dailyroutine.module.place.domain.PlaceIntro;
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
	
	/**댓글수*/
	private int likeCnt = 0;
	
	/**좋아요 수*/
	private int commentCnt = 0;
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;
	
	/**삭제 사유*/
	private String deleteReason = "";
	
	/**별점*/
	private int placeScore = 0;

	/**장소 회원별 인트로 글*/
	private List<PlaceIntroDto> introList = new ArrayList<>();

	/**회원아이디*/
	private String memberId = "";
	
	public Place toEntity() {
		return Place.builder().dto(this).build();
	}

	public PlaceDto(Place entity){
		this.placeNum = entity.getPlaceNum();
		this.title = entity.getTitle();
		this.categCd = entity.getCategCd();
		this.tel = entity.getTel();
		this.addr = entity.getAddr();
		this.mapx = entity.getMapx();
		this.mapy = entity.getMapy();
		this.useInfo = entity.getUseInfo();
		this.detailText = entity.getDetailText();
		this.pstatus = entity.getPstatus().name();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
		this.deleteReason = entity.getDeleteReason();
		if(!entity.getPlaceIntros().isEmpty()){
			int value = 0;
			int len = entity.getPlaceIntros().size();
			for(PlaceIntro score : entity.getPlaceIntros()){
				value += score.getScore();
			}
			this.placeScore = (value / len);
		}
	}

	public void addPlaceSummaryInfo(Place entity) {
		this.placeNum = entity.getPlaceNum();
		this.title = entity.getTitle();
		this.categCd = entity.getCategCd();
		this.tel = entity.getTel();
		this.addr = entity.getAddr();
		this.mapx = entity.getMapx();
		this.mapy = entity.getMapy();
		this.useInfo = entity.getUseInfo();
		this.detailText = entity.getDetailText();
		this.pstatus = entity.getPstatus().name();
		this.likeCnt = entity.getPlaceLikes().size();
		this.commentCnt = entity.getPlaceComments().size();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
		this.deleteReason = entity.getDeleteReason();
		if(!entity.getPlaceIntros().isEmpty()){
			int value = 0;
			int len = entity.getPlaceIntros().size();
			for(PlaceIntro score : entity.getPlaceIntros()){
				value += score.getScore();
			}
			this.placeScore = (value / len);
		}
	}

	public Map<String,Object> toSummaryMap(){
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("placeNum",this.placeNum);
		map.put("title",this.title);
		map.put("addr",this.addr);
		map.put("tel",this.tel);
		map.put("mapx",this.mapx);
		map.put("mapy",this.mapy);
		map.put("categCd",this.categCd);
		map.put("categNm",this.categNm);
		map.put("placeScore",this.placeScore);
		return map;
	}
	
}
