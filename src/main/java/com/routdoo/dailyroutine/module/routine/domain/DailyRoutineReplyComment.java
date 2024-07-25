package com.routdoo.dailyroutine.module.routine.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.common.EnableType;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineReplyCommentDto;
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
 * packageName    : com.routdoo.dailyroutine.module.routine.domain
 * fileName       : DailyRoutineCommentReply
 * author         : rhkdg
 * date           : 2023-12-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-04        rhkdg       최초 생성
 */
@Entity
@Table(name="daily_routine_comment_reply")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutineReplyComment {
    
    @Comment("일련번호")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("코멘트 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_idx",nullable = false)
    private DailyRoutineComment dailyRoutineComment;

    @Comment("회원 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id",nullable = false)
    private Member member;

    @Comment("내용")
    @Lob
    private String content;

    @Comment("활성화 여부")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,columnDefinition = "ENABLE")
    private EnableType enableType;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public DailyRoutineReplyComment(DailyRoutineReplyCommentDto dto){
        this.idx = dto.getIdx();
        this.dailyRoutineComment = new DailyRoutineComment();
        dailyRoutineComment.addIdx(dto.getCommentIdx());
        this.member = new Member();
        member.addId(dto.getMemberId());
        this.content = dto.getContent();
        this.enableType = EnableType.valueOf(dto.getEnableType());
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addIdx(Long idx){
        this.idx = idx;
    }

}
