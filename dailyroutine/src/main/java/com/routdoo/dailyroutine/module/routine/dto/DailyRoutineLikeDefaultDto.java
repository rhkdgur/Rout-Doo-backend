package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineLikeDefaultDto
 * author         : rhkdg
 * date           : 2023-12-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-07        rhkdg       최초 생성
 */
@Schema(description = "일정 좋아요 검색용")
@Getter
@Setter
public class DailyRoutineLikeDefaultDto extends BaseVo {
    
    /**회원 아이디*/
    private String memberId = "";
    
}
