package com.routdoo.dailyroutine.auth.member.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;
import com.routdoo.dailyroutine.auth.member.domain.QMemberFriends;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.auth.member.dto.MemberDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.repository.MemberCustomRepository;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;

import lombok.NoArgsConstructor;

@Repository
@NoArgsConstructor
public class MemberCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements MemberCustomRepository{
	
	/**
	 * 목록 조회
	 * @param searchDto
	 * @return
	 */
	private BooleanBuilder commonQuery(MemberDefaultDto searchDto) {
		QMember qMember = QMember.member;
		BooleanBuilder sql = new BooleanBuilder();
		if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()){
			if(searchDto.getStype().equals("nickname")){
				sql.and(qMember.nickname.like("%"+searchDto.getSstring()+"%"));
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
		if(member == null) {
			return null;
		}
		
		return new MemberDto(member);
	}

	@Override
	public boolean updateMemberFriendsBlockYn(MemberFriendsDto dto) throws Exception {
		QMember qMember = QMember.member;
		QMemberFriends qMemberFriends = QMemberFriends.memberFriends;
		return jpaQueryFactory.update(qMemberFriends)
				.set(qMemberFriends.blockYn,dto.getBlockYn())
				.where(new BooleanBuilder().and(qMember.id.eq(dto.getMemberId())).and(qMemberFriends.idx.eq(dto.getIdx())))
				.execute() > 0;
	}

	@Override
	public Page<Map<String, Object>> selectMemberFriendsBlockPageList(MemberDefaultDto searchDto) throws Exception {
		QMember qMember = QMember.member;
		QMemberFriends qMemberFriends = QMemberFriends.memberFriends;

		long cnt = jpaQueryFactory.select(qMember.count()).from(qMember)
				.join(qMemberFriends).fetchJoin()
				.where(new BooleanBuilder().and(qMember.id.eq(searchDto.getMemberId())).and(qMemberFriends.blockYn.eq(searchDto.getBlockYn())))
				.fetchFirst();

		List<MemberDto> list = jpaQueryFactory.select(
						Projections.bean(
								MemberDto.class,
								qMember.id,
								qMember.nickname,
								qMember.gender,
								qMember.age,
								qMember.mbti
						)
				)
				.from(qMember)
				.join(qMemberFriends).fetchJoin()
				.where(new BooleanBuilder().and(qMember.id.eq(searchDto.getMemberId())).and(qMemberFriends.blockYn.eq(searchDto.getBlockYn())))
				.offset(searchDto.getPageable().getOffset())
				.limit(searchDto.getPageable().getPageSize())
				.fetch();

		//Map 변환
		List<Map<String,Object>> resultList = list.stream().map(MemberDto::getSummaryInfo).toList();

		return new PageImpl<>(resultList,searchDto.getPageable(),cnt);
	}

	@Override
	public Page<Map<String, Object>> selectMemberFriendsPageList(MemberDefaultDto searchDto) throws Exception {
		QMember qMember = QMember.member;
		QMemberFriends qMemberFriends = QMemberFriends.memberFriends;

		long cnt = jpaQueryFactory.select(qMember.count()).from(qMember)
				.join(qMemberFriends).fetchJoin()
				.where(commonQuery(searchDto))
				.fetchFirst();

		List<MemberDto> list = jpaQueryFactory.select(
						Projections.bean(
								MemberDto.class,
								qMember.id,
								qMember.nickname,
								qMember.gender,
								qMember.age,
								qMember.mbti
						)
				)
				.from(qMember)
				.join(qMemberFriends).fetchJoin()
				.where(commonQuery(searchDto))
				.offset(searchDto.getPageable().getOffset())
				.limit(searchDto.getPageable().getPageSize())
				.fetch();

		//Map 변환
		List<Map<String,Object>> resultList = list.stream().map(MemberDto::getSummaryInfo).toList();

		return new PageImpl<>(resultList,searchDto.getPageable(),cnt);
	}

	/**
	 * 회원 페이징 목록
	 */
	@Override
	public Page<MemberDto> selectMemberPageList(MemberDefaultDto searchDto) throws Exception {
		QMember qMember = QMember.member;
		//회원 목록 
		List<Member> list = jpaQueryFactory.selectFrom(qMember)
							.where(commonQuery(searchDto))
							.offset(searchDto.getPageable().getOffset())
							.limit(searchDto.getPageable().getPageSize()).fetch();
		//회원 개수
		Long cnt = jpaQueryFactory.select(qMember.count()).from(qMember).where(commonQuery(searchDto)).fetchFirst();
		
		return new PageImpl<>(list.stream().map(x->new MemberDto(x)).collect(Collectors.toList()),searchDto.getPageable(),cnt);
	}
	
}
