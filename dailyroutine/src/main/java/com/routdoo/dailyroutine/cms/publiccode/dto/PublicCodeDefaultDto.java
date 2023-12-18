package com.routdoo.dailyroutine.cms.publiccode.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.cms.publiccode.dto
 * fileName       : PublicCodeDefaultDto
 * author         : GAMJA
 * date           : 2023/12/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/19        GAMJA       최초 생성
 */
@Getter @Setter
public class PublicCodeDefaultDto extends BaseVo {

    private String pubCd = "";

    private String parentCd= "";

    private String useYn = "";

}
