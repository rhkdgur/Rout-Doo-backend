package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;

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
@Getter @Setter
public class PlaceDefaultDto extends BaseVo{

	private static final long serialVersionUID = 1L;

	private long commentIdx = 0;

	/**사용상태*/
	private String pstatus = "";
	
	/**장소번호*/
	private String placeNum = "";
	
	/**제목(장소)*/
	private String title = "";
	
	/**카테고리*/
	private String categCd = "";
	
	/**주소*/
	private String addr = "";
	
	/**경도*/
	private String mapx = "";
	
	/**위도*/
	private String mapy = "";
	
	/**거리*/
	private int distance = 2;
	
}
