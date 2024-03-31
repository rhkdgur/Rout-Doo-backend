package com.routdoo.dailyroutine.module.place.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.place.dto.PlaceReplyCommentDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name="place_comment_reply")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PlaceReplyComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("코멘트 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_idx",nullable = false)
    private PlaceComment placeComment;

    @Comment("회원 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id",nullable = false)
    private Member member;

    @Comment("장소 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_num",nullable = false)
    private Place place;

    @Comment("내용")
    @Lob
    private String content;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public PlaceReplyComment(PlaceReplyCommentDto dto) {
        if (this.idx > 0) {
            this.idx = dto.getIdx();
        }
        this.placeComment = new PlaceComment();
        placeComment.addIdx(dto.getCommentIdx());
        this.member = new Member();
        this.member.addId(dto.getMemberDto().getId());
        this.place = new Place();
        this.place.addPlaceNum(dto.getPlaceNum());
        this.content = dto.getContent();
    }

}
