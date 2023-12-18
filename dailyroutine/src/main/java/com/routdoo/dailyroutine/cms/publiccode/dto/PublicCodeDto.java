package com.routdoo.dailyroutine.cms.publiccode.dto;

import com.routdoo.dailyroutine.cms.publiccode.domain.PublicCode;
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
public class PublicCodeDto implements Serializable {

    /**일련코드*/
    private String pubCd = "";

    /**부모코드*/
    private String parentCd = "";

    /**제목*/
    private String title = "";

    /**비고*/
    private String remark = "";

    /**기타1*/
    private String etc1 = "";

    /**기타2*/
    private String etc2 = "";

    /**기타3*/
    private String etc3 = "";

    /**사용여부*/
    private String useYn = "";

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
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
