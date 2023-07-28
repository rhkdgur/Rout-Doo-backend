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

	/**사용상태*/
	private String pstatus = "";
	
	/**장소*/
	private String placeNum = "";
	
}
