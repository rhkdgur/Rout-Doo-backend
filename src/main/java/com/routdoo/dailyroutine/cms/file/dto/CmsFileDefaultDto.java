package com.routdoo.dailyroutine.cms.file.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.cms.file.dto
 * fileName       : CmsFileDefaultDto
 * author         : GAMJA
 * date           : 2023/12/10
 * description    : cms File search dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        GAMJA       최초 생성
 */
@Getter
@Setter
public class CmsFileDefaultDto extends BaseVo {

    /**일련번호*/
    private Long idx = 0L;

    /**부모 pk*/
    private String parentIdx = "";

    /**업로드 코드*/
    private String uploadCode = "";

}
