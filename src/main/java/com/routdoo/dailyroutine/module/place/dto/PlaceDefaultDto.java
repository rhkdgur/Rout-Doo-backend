package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.dto
* @fileName      : PlaceDefaultDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.27
* @description   : 장소 검색 defaultDto
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.27        ghgo       최초 생성
 */
@Schema(description = "장소 검색 DTO")
@Getter @Setter
public class PlaceDefaultDto extends BaseVo{

	private static final long serialVersionUID = 1L;

	@Schema(description = "댓글 일련번호", example = "1")
	private long commentIdx = 0;

	@Schema(description = "사용상태", example = "Y(사용함),N(사용안함)")
	/**사용상태*/
	private String pstatus = "";

	@Schema(description = "장소번호", example = "P20230101001")
	/**장소번호*/
	private String placeNum = "";

	@Schema(description = "제목(장소)", example = "식당3선")
	/**제목(장소)*/
	private String title = "";

	@Schema(description = "카테고리 코드", example = "TEST")
	/**카테고리*/
	private String categCd = "";

	@Schema(description = "주소", example = "부산 해운대구 ... ")
	/**주소*/
	private String addr = "";

	@Schema(description = "경도", example = "31.1112121")
	/**경도*/
	private String mapx = "";

	@Schema(description = "위도", example = "127.1232123")
	/**위도*/
	private String mapy = "";

	@Schema(description = "내 주변 거리 ", example = "1")
	/**거리*/
	private int distance = 2;

	@Schema(description = "인기여부", example = "적용 : true, 미적용 : false")
	/**인기여부*/
	private boolean populFlag = false;
	
}
