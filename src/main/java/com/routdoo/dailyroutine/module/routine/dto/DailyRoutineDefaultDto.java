package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
@Schema(description = "일상 검색용 DTO")
@Getter
@Setter
public class DailyRoutineDefaultDto extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**일상 일련번호*/
	@Schema(description = "일상 일련번호")
	private Long idx = 0L;

	@Schema(description = "유저 아이디")
	private String memberId= "";
	
	/**공개여부*/
	@Schema(description = "공개 범위 지정")
	private String rangeType = "";
	
	/**시작일자*/
	@Schema(description = "시작일자")
	private String startDate = "";
	
	/**마지막일자*/
	@Schema(description = "마지막일자")
	private String endDate = "";
	
	/**스케줄 목록 날짜*/
	@Schema(description = "스케줄 목록 날짜")
	private String toDate = "";

	private String tag = "";

	private List<String> tags = new ArrayList<>();

	/**
	 * 커뮤니티 검색
	 */
	@Schema(description = "커뮤니티에서 검색할때 사용 (백엔드에서 이용)")
	private boolean isCummunity = false;
}
