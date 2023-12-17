package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "장소 좋아요 검색용 DTO")
@Getter
@Setter
public class PlaceLikeDefaultDto extends BaseVo {

    /**회원 정보*/
    @Schema(description = "회원 아이디")
    private String memberId = "";
    
}
