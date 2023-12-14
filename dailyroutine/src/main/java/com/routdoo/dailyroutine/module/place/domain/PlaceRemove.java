package com.routdoo.dailyroutine.module.place.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.place.dto.PlaceRemoveDto;
import com.routdoo.dailyroutine.module.place.service.PlaceRemoveType;
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
 * fileName       : PlaceRemove
 * author         : rhkdg
 * date           : 2023-12-14
 * description    : 장소 삭제 요청 내역 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        rhkdg       최초 생성
 */
@Entity
@Table(name="place_remove")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PlaceRemove {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_num")
    private Place place;

    @Comment("삭제 요청 사유")
    @Lob
    private String deleteReason;

    @Comment("거절 사유")
    @Lob
    private String rejectReason;

    @Comment("승인여부")
    @Enumerated(EnumType.STRING)
    private PlaceRemoveType approveType;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;


    @Builder
    public PlaceRemove(PlaceRemoveDto dto) {
        if(dto.getIdx() > 0){
            this.idx = dto.getIdx();
        }
        this.member = new Member();
        member.addId(dto.getMemberId());
        this.place = new Place();
        place.addPlaceNum(dto.getPlaceNum());
        this.deleteReason = dto.getDeleteReason();
        this.rejectReason = dto.getRejectReason();
        this.approveType = PlaceRemoveType.valueOf(dto.getApproveType());
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

}
