package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "일정 타임라인 검색용 DTO")
@Getter @Setter
public class DailyRoutineTimeLineDefaultDto extends BaseVo{

	private static final long serialVersionUID = 1L;

	/**일련번호*/
	@Schema(description = "일정 타임라인 일련번호")
	private Long idx = 0L;
	
	/**일상 일련번호*/
	@Schema(description = "일정 일련번호")
	private Long dailyIdx = 0L;
	
}
