package com.routdoo.dailyroutine.module.routine.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentDto;
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
 * fileName       : DailyRoutineComment
 * author         : rhkdg
 * date           : 2023-12-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-04        rhkdg       최초 생성
 */
@Entity
@Table(name="daily_routine_comment")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutineComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("회원 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id",nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Comment("일정 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="daily_idx",nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private DailyRoutine dailyRoutine;
    
    @Comment("내용")
    @Lob
    private String context;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public DailyRoutineComment(DailyRoutineCommentDto dto){
        this.idx = dto.getIdx();
        this.member = new Member();
        member.addId(dto.getMemberId());
        this.dailyRoutine = new DailyRoutine();
        dailyRoutine.addIdx(dto.getDailyIdx());
        this.context = dto.getContext();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addIdx(Long idx){
        this.idx = idx;
    }

}
