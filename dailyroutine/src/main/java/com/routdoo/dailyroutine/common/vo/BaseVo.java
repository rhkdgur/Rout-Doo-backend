package com.routdoo.dailyroutine.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

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
@Getter @Setter
public class BaseVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**공통 입력*/
	private String sstring = "";
	
	/**공통 타입*/
	private String stype = "";
	
	private int page = 1;
	
	private int size = 10;
	
	private Sort sort = null;
	
	private Map<String,String> params = new HashMap<>();
	
	public Pageable getPageable() {
		if(sort != null) {
			return PageRequest.of((this.page - 1),this.size,sort);
		}
		return PageRequest.of((this.page - 1),this.size);
	}

}
