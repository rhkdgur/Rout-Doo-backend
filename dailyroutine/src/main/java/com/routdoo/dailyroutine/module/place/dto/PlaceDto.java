package com.routdoo.dailyroutine.module.place.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.Place;

import com.routdoo.dailyroutine.module.place.domain.PlaceIntro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "장소 DTO")
@Getter
@Setter
@NoArgsConstructor
public class PlaceDto {

	@Schema(description = "장소번호", example = "P20230202001")
	/**일련번호*/
	private String placeNum;

	@Schema(description = "제목", example = "장충동왕족발보쌈")
	@NotBlank
	/**제목*/
	private String title;

	@Schema(description = "연락처", example = "051-000-0000")
	@NotNull
	/**연락처*/
	private String tel;

	@Schema(description = "카테고리 코드", example = "TEST")
	@NotBlank
	/**카테고리 코드*/
	private String categCd;

	@Schema(description = "카테고리 명칭", example = "테스트")
	/***카테고리 명*/
	private String categNm;

	@Schema(description = "주소", example = "부산 해운대구 ...")
	@NotEmpty
	/**주소*/
	private String addr;

	@Schema(description = "경도", example = "31.2321231")
	@NotEmpty
	/**경도*/
	private String mapx;

	@Schema(description = "위도", example = "127.1241231")
	@NotEmpty
	/**위도*/
	private String mapy;

	@Schema(description = "이용안내", example = "이용안내")
	@NotEmpty
	/**이용안내*/
	private String useInfo;

	@Schema(description = "상세정보", example = "상세정보")
	@NotEmpty
	/**상세정보*/
	private String detailText;

	@Schema(description = "사용여부", example = "사용 : Y, 미사용 : N")
	@NotBlank
	/**사용여부*/
	private String pstatus;

	@Schema(description = "좋아요수", example = "1")
	/**좋아요 수*/
	private int likeCnt = 0;

	@Schema(description = "댓글수", example = "1")
	/**댓글수*/
	private int commentCnt = 0;

	@Schema(description = "등록일자", example = "2023-01-01 00:00:00 ")
	/**등록일자*/
	private LocalDateTime createDate;

	@Schema(description = "수정일자", example = "2023-01-01 00:00:00 ")
	/**수정일자*/
	private LocalDateTime modifyDate;

	@Schema(description = "별점", example = "4 ")
	/**별점*/
	private int placeScore = 0;
	
	/**회원 아이디*/
	private String memberId = "";

	@Schema(description = "회원별 리뷰 정보", example = "")
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
	
}
