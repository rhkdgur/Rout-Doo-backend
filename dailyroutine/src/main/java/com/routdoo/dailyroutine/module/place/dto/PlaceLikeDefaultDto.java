package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceLikeDefaultDto
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
public class PlaceLikeDefaultDto extends BaseVo {

    /**회원 정보*/
    private String memberId = "";
    
}
