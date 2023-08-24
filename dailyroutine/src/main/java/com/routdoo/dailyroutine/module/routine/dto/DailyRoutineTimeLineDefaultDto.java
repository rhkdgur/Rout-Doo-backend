package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.dto
* @fileName      : DailyRoutineTimeLineDefaultDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.14
* @description   : 일상 타임라인 defaultDto
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.14        ghgo       최초 생성
 */
@Getter @Setter
public class DailyRoutineTimeLineDefaultDto extends BaseVo{

	private static final long serialVersionUID = 1L;

	/**일련번호*/
	private Long idx = 0L;
	
	/**일상 일련번호*/
	private Long dailyIdx = 0L;
	
}
