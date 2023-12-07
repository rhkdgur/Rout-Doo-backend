package com.routdoo.dailyroutine.module.routine.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineInvite;
import com.routdoo.dailyroutine.module.routine.domain.QDailyRoutine;
import com.routdoo.dailyroutine.module.routine.domain.QDailyRoutineInvite;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineInviteDto;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineInviteCustomRepository;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineInviteRepository;
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
     * @param dto
     * @return
     */
    @Override
    public List<DailyRoutineInviteDto> selectDailyRoutineInviteDtoList(DailyRoutineInviteDto dto) {
        QDailyRoutineInvite qDailyRoutineInvite = QDailyRoutineInvite.dailyRoutineInvite;
        QMember qMember = QMember.member;

        List<DailyRoutineInviteDto> resultList = new ArrayList<>();

        List<Tuple> list = jpaQueryFactory.select(
                    qDailyRoutineInvite.idx,
                    qDailyRoutineInvite.dailyRoutine.idx,
                    qMember.id,
                    qMember.nickname
                )
                .from(qDailyRoutineInvite)
                .join(qMember).fetchJoin()
                .where(qDailyRoutineInvite.dailyRoutine.idx.eq(dto.getDailyIdx()))
                .fetch();

        for(Tuple tp : list){
            DailyRoutineInviteDto inviteDto = new DailyRoutineInviteDto();
            inviteDto.setIdx(tp.get(qDailyRoutineInvite.idx));
            inviteDto.setDailyIdx(tp.get(qDailyRoutineInvite.dailyRoutine.idx));
            inviteDto.getMember().setId(tp.get(qMember.id));
            inviteDto.getMember().setNickname(tp.get(qMember.nickname));
            resultList.add(inviteDto);
        }

        return resultList;
    }
}
