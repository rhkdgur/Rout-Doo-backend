package com.routdoo.dailyroutine.module.routine.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineLikeDto;
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
 * fileName       : DailyRoutineLike
 * author         : rhkdg
 * date           : 2023-11-26
 * description    : 일정 좋아요 Entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-26        rhkdg       최초 생성
 */
@Entity
@Getter
@Table(name="daily_routine_like")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyRoutineLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    
    @Comment("일정 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_idx",nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private DailyRoutine dailyRoutine;
    
    @Comment("회원 아이디")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Comment("등록일자")
    @CreatedDate
    private LocalDateTime createDate;

    @Comment("수정일자")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Builder
    public DailyRoutineLike(DailyRoutineLikeDto dto){
        if(this.idx > 0){
            this.idx = dto.getIdx();
        }
        this.dailyRoutine = dto.getDailyRoutineDto().toEntity();
        this.member.addId(dto.getMemberId());
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

}
