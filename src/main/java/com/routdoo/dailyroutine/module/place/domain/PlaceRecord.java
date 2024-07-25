package com.routdoo.dailyroutine.module.place.domain;

import com.querydsl.core.annotations.QueryProjection;
import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordDto;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.domain
 * fileName       : PlaceRecord
 * author         : rhkdg
 * date           : 2023-12-14
 * description    : 장소 정보 수정 내역
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        rhkdg       최초 생성
 */
@Entity
@Table(name="place_record")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PlaceRecord {

    @Comment("일련번호")
    @Id @GeneratedValue
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_num")
    private Place place;

    @Comment("연락처")
    @Column(length= 50)
    private String tel;

    @Comment("주소")
    private String addr;

    @Comment("경도")
    private String mapx;

    @Comment("위도")
    private String mapy;

    @Comment("이용안내")
    @Lob
    private String useInfo;

    @Comment("상세내용")
    @Lob
    private String detailText;

    @Comment("사용여부")
    @Enumerated(EnumType.STRING)
    private PlaceStatusType useType;

    @CreatedDate
    @Comment("등록일자")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Comment("수정일자")
    private LocalDateTime modifyDate;


    @Builder
    public PlaceRecord(PlaceRecordDto dto){
        if(dto.getIdx() > 0){
            this.idx = dto.getIdx();
        }
        this.member = new Member();
        member.addId(dto.getMemberId());
        this.place = new Place();
        place.addPlaceNum(dto.getPlaceNum());
        this.tel = dto.getTel();
        this.addr = dto.getAddr();
        this.mapx = dto.getMapx();
        this.mapy = dto.getMapy();
        this.useInfo = dto.getUseInfo();
        this.detailText = dto.getDetailText();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
        this.useType = PlaceStatusType.valueOf(dto.getUseType());
    }

}
