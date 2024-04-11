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

	protected static final long serialVersionUID = 1L;

	protected boolean fullTextSearch = false;
	
	/**공통 입력*/
	protected String sstring = "";
	
	/**공통 타입*/
	protected String stype = "";
	
	protected int page = 1;
	
	protected int size = 10;
	
	protected Sort sort = null;
	
	protected Map<String,String> params = new HashMap<>();
	
	public Pageable getPageable() {
		if(sort != null) {
			return PageRequest.of((this.page - 1),this.size,sort);
		}
		return PageRequest.of((this.page - 1),this.size);
	}

}
