package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineCommentDefaultDto
 * author         : GAMJA
 * date           : 2024/03/31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/03/31        GAMJA       최초 생성
 */
@Getter
@Setter
public class DailyRoutineCommentDefaultDto extends BaseVo {

    private Long dailyIdx = 0L;

    private String memberId = "";
}
