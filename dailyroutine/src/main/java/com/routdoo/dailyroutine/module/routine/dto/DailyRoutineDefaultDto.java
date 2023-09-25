package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;

import lombok.Getter;
import lombok.Setter;

/***
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.dto
* @fileName      : DailyRoutineDefaultDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.13
* @description   : 일상 defaultDto
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.13        ghgo       최초 생성
 */
@Getter
@Setter
public class DailyRoutineDefaultDto extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**일상 일련번호*/
	private Long idx = 0L;
	
	/**시작일자*/
	private String startDate = "";
	
	/**마지막일자*/
	private String endDate = "";
	
	/**스케줄 목록 날짜*/
	private String toDate = "";
}
