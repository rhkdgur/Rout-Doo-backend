package com.routdoo.dailyroutine.module.place.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.place.dto.PlaceIntroDto;
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
 * fileName       : PlaceReview
 * author         : GAMJA
 * date           : 2023/12/13
 * description    : 장소 인트로 정보 Entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/13        GAMJA       최초 생성
 */
@Entity
@Table(name="place_intro_info")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PlaceIntro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_num")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Comment("소개글")
    @Lob
    private String introText;

    @Comment("방문일자")
    @Column(length = 20)
    private String visitDate;

    @Comment("별점")
    private int score;

    @CreatedDate
    @Comment("등록일자")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Comment("수정일자")
    private LocalDateTime modifyDate;

    @Builder
    public PlaceIntro(PlaceIntroDto dto) {
        this.idx = dto.getIdx();
        this.place = new Place();
        this.place.addPlaceNum(dto.getPlaceNum());
        this.member = new Member();
        member.addId(dto.getMemberId());
        this.introText = dto.getIntroText();
        this.visitDate = dto.getVisitDate();
        this.score = dto.getScore();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addIdx(Long idx) {
        this.idx = idx;
    }

    public void addPlace(Place place) {
        this.place = place;
    }

    public void addMember(Member member){
        this.member = member;
    }

    public void addChangePlaceIntro(PlaceIntro placeIntro) {
        this.introText = placeIntro.getIntroText();
        this.visitDate = placeIntro.getVisitDate();
        this.score = placeIntro.getScore();
    }
}
