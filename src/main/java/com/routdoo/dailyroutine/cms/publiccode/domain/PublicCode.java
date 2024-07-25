package com.routdoo.dailyroutine.cms.publiccode.domain;

import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.cms.category.domain
 * fileName       : Category
 * author         : GAMJA
 * date           : 2023/12/19
 * description    : 공통 코드 관리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/19        GAMJA       최초 생성
 */
@Entity
@Table(name="cms_public_code")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PublicCode implements Persistable<String> {

    @Comment("코드")
    @Id
    @Column(length = 50)
    private String pubCd;

    @Comment("부모코드")
    @Column(length = 50)
    private String parentCd;

    @Comment("제목")
    @Column(length = 200)
    private String title;

    @Comment("비고")
    @Lob
    private String remark;

    @Comment("기타1")
    @Column(length = 200)
    private String etc1;

    @Comment("기타2")
    @Column(length = 200)
    private String etc2;

    @Comment("기타3")
    @Column(length = 200)
    private String etc3;

    @Comment("사용여부")
    @Column(length = 1)
    private String useYn;
    
    @Comment("정렬")
    private int ord;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public PublicCode(PublicCodeDto dto){
        this.pubCd = dto.getPubCd();
        this.parentCd = dto.getParentCd();
        this.title = dto.getTitle();
        this.remark = dto.getRemark();
        this.etc1 = dto.getEtc1();
        this.etc2 = dto.getEtc2();
        this.etc3 = dto.getEtc3();
        this.useYn = dto.getUseYn();
        this.ord = dto.getOrd();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    @Override
    public String getId() {
        return this.pubCd;
    }

    @Override
    public boolean isNew() {
        return this.pubCd.isEmpty();
    }
}
