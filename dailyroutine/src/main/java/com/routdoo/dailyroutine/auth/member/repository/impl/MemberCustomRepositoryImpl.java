package com.routdoo.dailyroutine.auth.member.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.auth.member.domain.QMemberFriends;
import com.routdoo.dailyroutine.auth.member.dto.*;
import com.routdoo.dailyroutine.auth.member.repository.MemberCustomRepository;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@NoArgsConstructor
public class MemberCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements MemberCustomRepository {

    /**
     * 목록 조회
     *
     * @param searchDto
     * @return
     */
    private BooleanBuilder commonQuery(MemberDefaultDto searchDto) {
        QMember qMember = QMember.member;
        BooleanBuilder sql = new BooleanBuilder();
        if (searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()) {
            if (searchDto.getStype().equals("nickname")) {
                sql.and(qMember.nickname.like("%" + searchDto.getSstring() + "%"));
            }
        }

        if(searchDto.isExclude()){
            if(searchDto.getExcludeType().equals("myself")){
               sql.and(qMember.id.ne(searchDto.getMemberId()));
            }
        }else {
            if (searchDto.getMemberId() != null && !searchDto.getMemberId().isEmpty()) {
                sql.and(qMember.id.eq(searchDto.getMemberId()));
            }
        }

        return sql;
    }


    /**
     * 회원 목록 , no paging
     */
    @Override
    public List<MemberDto> selectMemberList(MemberDefaultDto searchDto) throws Exception {
        QMember qMember = QMember.member;

        List<Member> list = jpaQueryFactory.selectFrom(qMember).where(commonQuery(searchDto)).fetch();

        return list.stream().map(MemberDto::new).collect(Collectors.toList());
    }

    /**
     * 회원 상세
     */
    @Override
    public MemberDto selectMember(MemberDto dto) throws Exception {
        QMember qMember = QMember.member;

        Member member = jpaQueryFactory.selectFrom(qMember).where(new BooleanBuilder().and(qMember.id.eq(dto.getId()))).fetchOne();
        if (member == null) {
            return null;
        }

        return new MemberDto(member);
    }

    @Override
    public boolean updateMemberFriendsBlockYn(MemberFriendsDto dto) throws Exception {
        QMember qMember = QMember.member;
        QMemberFriends qMemberFriends = QMemberFriends.memberFriends;
        return jpaQueryFactory.update(qMemberFriends)
                .set(qMemberFriends.blockYn, dto.getBlockYn())
                .where(new BooleanBuilder().and(qMember.id.eq(dto.getMemberId())).and(qMemberFriends.idx.eq(dto.getIdx())))
                .execute() > 0;
    }

    @Override
    public boolean updateMemberInfo(MemberDto memberDto) throws Exception {
        QMember qMember = QMember.member;
        return jpaQueryFactory.update(qMember)
                .set(qMember.nickname, memberDto.getNickname())
                .set(qMember.mbti,memberDto.getMbti())
                .set(qMember.introText, memberDto.getIntroText())
                .where(new BooleanBuilder().and(qMember.id.eq(memberDto.getId())))
                .execute() > 0;
    }

    @Override
    public boolean updateMemberUseStatus(MemberDto dto) throws Exception {
        QMember qMember = QMember.member;
        return jpaQueryFactory.update(qMember)
                .set(qMember.useStatus, dto.getUseStatus())
                .where(new BooleanBuilder().and(qMember.id.eq(dto.getId())))
                .execute() > 0;
    }

    @Override
    public Page<MemberFriendResponse> selectMemberFriendsBlockPageList(MemberDefaultDto searchDto) throws Exception {
        QMember qMember = QMember.member;
        QMemberFriends qMemberFriends = QMemberFriends.memberFriends;

        long cnt = jpaQueryFactory.select(qMember.count()).from(qMember)
                .join(qMemberFriends).on(qMember.id.eq(qMemberFriends.member.id)).fetchJoin()
                .where(new BooleanBuilder().and(qMember.id.eq(searchDto.getMemberId())).and(qMemberFriends.blockYn.eq(searchDto.getBlockYn())))
                .fetchFirst();

        List<MemberFriendResponse> list = jpaQueryFactory.select(
                        Projections.bean(
                                MemberFriendResponse.class,
                                qMemberFriends.invitedId,
                                qMember.nickname,
                                qMember.gender,
                                qMember.age,
                                qMember.mbti,
                                qMemberFriends.blockYn
                        )
                )
                .from(qMember)
                .join(qMemberFriends).on(qMember.id.eq(qMemberFriends.member.id)).fetchJoin()
                .where(new BooleanBuilder().and(qMember.id.eq(searchDto.getMemberId())).and(qMemberFriends.blockYn.eq(searchDto.getBlockYn())))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list, searchDto.getPageable(), cnt);
    }

    @Override
    public List<MemberFriendResponse> selectMemberFriendsBlockList(MemberDefaultDto searchDto) throws Exception {
        QMember qMember = QMember.member;
        QMemberFriends qMemberFriends = QMemberFriends.memberFriends;


        List<MemberFriendResponse> list = jpaQueryFactory.select(
                        Projections.bean(
                                MemberFriendResponse.class,
                                qMemberFriends.invitedId,
                                qMember.nickname,
                                qMember.gender,
                                qMember.age,
                                qMember.mbti,
                                qMemberFriends.blockYn
                        )
                )
                .from(qMember)
                .join(qMemberFriends).on(qMember.id.eq(qMemberFriends.member.id)).fetchJoin()
                .where(new BooleanBuilder().and(qMember.id.eq(searchDto.getMemberId())).and(qMemberFriends.blockYn.eq(searchDto.getBlockYn())))
                .fetch();

        return list;
    }


    @Override
    public Page<MemberFriendResponse> selectMemberFriendsPageList(MemberDefaultDto searchDto) throws Exception {
        QMember qMember = QMember.member;
        QMemberFriends qMemberFriends = QMemberFriends.memberFriends;

        long cnt = jpaQueryFactory.select(qMember.count()).from(qMember)
                .join(qMemberFriends).on(qMember.id.eq(qMemberFriends.member.id)).fetchJoin()
                .where(commonQuery(searchDto))
                .fetchFirst();

        List<MemberFriendResponse> list = jpaQueryFactory.select(
                        Projections.bean(
                                MemberFriendResponse.class,
                                qMemberFriends.invitedId,
                                qMember.nickname,
                                qMember.gender,
                                qMember.age,
                                qMember.mbti,
                                qMemberFriends.blockYn
                        )
                ).from(qMember)
                .join(qMemberFriends).on(qMember.id.eq(qMemberFriends.invitedId)).fetchJoin()
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list, searchDto.getPageable(), cnt);
    }

    @Override
    public List<MemberFriendResponse> selectMemberFriendsList(MemberDefaultDto searchDto) throws Exception {
        QMember qMember = QMember.member;
        QMemberFriends qMemberFriends = QMemberFriends.memberFriends;

        List<MemberFriendResponse> list = jpaQueryFactory.select(
                        Projections.bean(
                                MemberFriendResponse.class,
                                qMemberFriends.invitedId,
                                qMember.nickname,
                                qMember.gender,
                                qMember.age,
                                qMember.mbti,
                                qMemberFriends.blockYn
                        )
                )
                .from(qMember)
                .join(qMemberFriends).on(qMember.id.eq(qMemberFriends.member.id)).fetchJoin()
                .where(commonQuery(searchDto)).fetch();

        return list;
    }

    /**
     * 회원 페이징 목록
     */
    @Override
    public Page<MemberSummaryResponse> selectMemberPageList(MemberDefaultDto searchDto) throws Exception {
        QMember qMember = QMember.member;
        //회원 목록
        List<MemberSummaryResponse> list = jpaQueryFactory.select(
                        Projections.constructor(
                                MemberSummaryResponse.class,
                                qMember.id,
                                qMember.nickname,
                                qMember.gender,
                                qMember.age,
                                qMember.introText,
                                qMember.mbti
                        )
                )
                .from(qMember)
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize()).fetch();
        //회원 개수
        Long cnt = jpaQueryFactory.select(qMember.count()).from(qMember).where(commonQuery(searchDto)).fetchFirst();

        return new PageImpl<>(list, searchDto.getPageable(), cnt);
    }

}
