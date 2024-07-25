package com.routdoo.dailyroutine.common.web;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.common.web
* @fileName      : BaseController.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.13
* @description   : Base 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.13        ghgo       최초 생성
 */
public class BaseController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**결과 처리 Map*/
	protected Map<String,Object> modelMap = null;

	protected final String MGN_URL = "/mgn";

	//modelMap 초기화
	@ModelAttribute
	protected void initModelMap(){
		modelMap = new LinkedHashMap<>();
	}
}
