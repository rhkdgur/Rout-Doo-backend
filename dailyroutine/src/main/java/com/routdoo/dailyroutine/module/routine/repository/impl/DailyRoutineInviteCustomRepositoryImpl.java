package com.routdoo.dailyroutine.module.routine.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.routine.domain.QDailyRoutineInvite;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteResponse;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineInviteCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.repository.impl
 * fileName       : DailyRoutineInviteCustomRepositoryImpl
 * author         : rhkdg
 * date           : 2023-12-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-04        rhkdg       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class DailyRoutineInviteCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements DailyRoutineInviteCustomRepository {

    /**
     * 일정 초대인원 목록 조회
     *
     * @param dto
     * @return
     */
    @Override
    public List<DailyRoutineInviteResponse> selectDailyRoutineInviteDtoList(DailyRoutineInviteDto dto) {
        QDailyRoutineInvite qDailyRoutineInvite = QDailyRoutineInvite.dailyRoutineInvite;
        QMember qMember = QMember.member;

        BooleanBuilder sql = new BooleanBuilder();
        if(dto.getDailyIdx() > 0) {
            sql.and(qDailyRoutineInvite.dailyRoutine.idx.eq(dto.getDailyIdx()));
        }
        if(dto.getMemberIds().size() > 0) {
            List<String> membersId = new ArrayList<>();
            for(String memberId : dto.getMemberIds()){
                membersId.add(memberId);
            }
            sql.and(qMember.id.in(membersId));
        }
        if(dto.getMemberId() != null && !dto.getMemberId().isEmpty()) {
            sql.and(qMember.id.eq(dto.getMemberId()));
        }

        List<DailyRoutineInviteResponse> list = jpaQueryFactory.select(
                        Projections.constructor(
                                DailyRoutineInviteResponse.class,
                                qDailyRoutineInvite.idx,
                                qDailyRoutineInvite.dailyRoutine.idx,
                                qMember.id,
                                qMember.nickname,
                                qMember.gender,
                                qDailyRoutineInvite.createDate,
                                qDailyRoutineInvite.modifyDate
                        )
                )
                .from(qDailyRoutineInvite)
                .join(qMember).on(qMember.id.eq(qDailyRoutineInvite.member.id)).fetchJoin()
                .where(sql)
                .fetch();

        return list;
    }
}
