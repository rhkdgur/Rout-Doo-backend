package com.routdoo.dailyroutine.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.common.vo
* @fileName      : BaseVo.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.12
* @description   : 공통 VO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.12        ghgo       최초 생성
 */
@Schema(description = "공통 검색용")
@Getter @Setter
public class BaseVo implements Serializable{

	protected boolean fullTextSearch = false;
	
	/**공통 입력*/
	@Schema(description = "검색(input)")
	protected String sstring = "";
	
	/**공통 타입*/
	@Schema(description = "검색타입")
	protected String stype = "";

	@Schema(description = "페이지 번호")
	protected int page = 1;

	@Schema(description = "페이지 데이터 출력 개수")
	protected int size = 10;

	@Schema(description = "정렬 함수(백엔드에서 사용)")
	protected Sort sort = null;

	public Pageable getPageable() {
		if(sort != null) {
			return PageRequest.of((this.page - 1),this.size,sort);
		}
		return PageRequest.of((this.page - 1),this.size);
	}

}
