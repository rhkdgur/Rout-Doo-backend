package com.routdoo.dailyroutine.cms.publiccode.dto;

import com.routdoo.dailyroutine.cms.publiccode.domain.PublicCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.cms.publiccode.dto
 * fileName       : PublicCodeDto
 * author         : GAMJA
 * date           : 2023/12/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/19        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "공통코드관리 Dto")
public class PublicCodeDto implements Serializable {

    /**일련코드*/
    @Schema(description = "코드(소분류)")
    private String pubCd = "";

    /**부모코드*/
    @Schema(description = "상위코드(대분류)")
    private String parentCd = "";

    /**제목*/
    @Schema(description = "제목")
    private String title = "";

    /**비고*/
    @Schema(description = "비고")
    private String remark = "";

    /**기타1*/
    @Schema(description = "기타1")
    private String etc1 = "";

    /**기타2*/
    @Schema(description = "기타2")
    private String etc2 = "";

    /**기타3*/
    @Schema(description = "기타3")
    private String etc3 = "";

    /**사용여부*/
    @Schema(description = "사용여부"
            ,example = "Y(사용함), N(사용안함)"
    )
    private String useYn = "";

    /**등록일자*/
    @Schema(description = "등록일자")
    private LocalDateTime createDate;

    /**수정일자*/
    @Schema(description = "수정일자")
    private LocalDateTime modifyDate;

    public PublicCode toEntity(){
        return PublicCode.builder().dto(this).build();
    }

    public PublicCodeDto(PublicCode entity){
        this.pubCd = entity.getPubCd();
        this.parentCd = entity.getParentCd();
        this.title = entity.getTitle();
        this.remark = entity.getRemark();
        this.etc1 = entity.getEtc1();
        this.etc2 = entity.getEtc2();
        this.etc3 = entity.getEtc3();
        this.useYn = entity.getUseYn();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

}
