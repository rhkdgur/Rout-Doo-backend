package com.routdoo.dailyroutine.cms.board.group.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.cms.board.group.dto
 * fileName       : BoardGroupDefaultDto
 * author         : rhkdg
 * date           : 2024-01-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-30        rhkdg       최초 생성
 */
@Getter
@Setter
public class BoardGroupDefaultDto extends BaseVo {

    /**게시판 코드*/
    private String gcode = "";

    /**제목*/
    private String title = "";

    /*공개비공개여부*/
    private String publicYn = "";

    /*사용여부*/
    private String useYn = "";

}
