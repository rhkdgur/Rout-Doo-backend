package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
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
@Getter
@Setter
public class DailyRoutineLikeDefaultDto extends BaseVo {
    
    /**회원 아이디*/
    private String memberId = "";
    
}
