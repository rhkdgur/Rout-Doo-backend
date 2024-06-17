package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.domain.PlaceIntro;
import com.routdoo.dailyroutine.module.place.dto.action.PlaceCreateRequest;
import com.routdoo.dailyroutine.module.place.dto.action.PlaceUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
@Schema(description = "장소 DTO")
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

	/**좋아요 수*/
	private int likeCnt = 0;

	/**댓글수*/
	private int commentCnt = 0;

	/**등록일자*/
	private LocalDateTime createDate;

	/**수정일자*/
	private LocalDateTime modifyDate;

	/**별점*/
	private int placeScore = 0;
	
	/**회원 아이디*/
	private String memberId = "";

	private MultipartFile multipartFile;

	/**장소 회원별 인트로 글*/
	private List<PlaceIntroDto> introList = new ArrayList<>();
	
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

	/**
	 * 등록을 위한 action dto
	 * @param placeActionRequest
	 * @return
	 */
	public static PlaceDto createOf(PlaceCreateRequest placeActionRequest){
		PlaceDto create = new PlaceDto();
		create.setTitle(placeActionRequest.getTitle());
		create.setTel(placeActionRequest.getTel());
		create.setCategCd(placeActionRequest.getCategCd());
		create.setAddr(placeActionRequest.getAddr());
		create.setMapx(placeActionRequest.getMapx());
		create.setMapy(placeActionRequest.getMapy());
		create.setUseInfo(placeActionRequest.getUseInfo());
		create.setDetailText(placeActionRequest.getDetailText());
		create.setPstatus(placeActionRequest.getPstatus());
		return create;
	}

	/**
	 * 수정
	 * @param placeActionRequest
	 * @return
	 */
	public static PlaceDto updateOf(PlaceUpdateRequest placeActionRequest){
		PlaceDto update = new PlaceDto();
		update.setPlaceNum(placeActionRequest.getPlaceNum());
		update.setTitle(placeActionRequest.getTitle());
		update.setTel(placeActionRequest.getTel());
		update.setCategCd(placeActionRequest.getCategCd());
		update.setAddr(placeActionRequest.getAddr());
		update.setMapx(placeActionRequest.getMapx());
		update.setMapy(placeActionRequest.getMapy());
		update.setUseInfo(placeActionRequest.getUseInfo());
		update.setDetailText(placeActionRequest.getDetailText());
		update.setPstatus(placeActionRequest.getPstatus());
		return update;
	}
	
}
