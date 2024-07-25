package com.routdoo.dailyroutine.auth.member.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.auth.member.domain.MemberMyspot;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.auth.member.domain.QMemberMyspot;
import com.routdoo.dailyroutine.auth.member.dto.MemberMyspotDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberMyspotDto;
import com.routdoo.dailyroutine.auth.member.repository.MemberMyspotCustomRepository;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;

@Repository
public class MemberMyspotCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements MemberMyspotCustomRepository{

	/**
	 * 나만의 장소 
	 * @return
	 */
	private BooleanBuilder commonQuery(MemberMyspotDefaultDto searchDto) {
		BooleanBuilder sql = new BooleanBuilder();
		return sql;
	}
	
	/**
	 * 나만의 장소 목록
	 */
	@Override
	public Page<MemberMyspotDto> selectMemberMyspotList(MemberMyspotDefaultDto searchDto) throws Exception {
		
		QMember qMember = QMember.member;
		QMemberMyspot myspot = QMemberMyspot.memberMyspot;
		
		Long cnt = jpaQueryFactory.select(myspot.count()).from(myspot)
				.leftJoin(qMember).fetchJoin()
				.where(commonQuery(searchDto))
				.fetchFirst();
		
		List<MemberMyspot> list = jpaQueryFactory.selectFrom(myspot)
				.leftJoin(qMember).fetchJoin()
				.where(commonQuery(searchDto))
				.offset(searchDto.getPageable().getOffset())
				.limit(searchDto.getPageable().getPageSize()).fetch();
		
		return new PageImpl<>(list.stream().map(x->new MemberMyspotDto(x)).collect(Collectors.toList()),searchDto.getPageable(),cnt);
	}

	/**
	 * 나만의 장소 상세 조회
	 */
	@Override
	public MemberMyspotDto selectMemberMyspot(MemberMyspotDto dto) throws Exception {
		QMemberMyspot qMyspot = QMemberMyspot.memberMyspot;
		QMember qMember = QMember.member;
		
		MemberMyspot myspot = jpaQueryFactory.selectFrom(qMyspot).join(qMember)
				.where(new BooleanBuilder().and(qMyspot.idx.eq(dto.getIdx()))).fetchFirst();
		
		return new MemberMyspotDto(myspot);
	}

	
	/**
	 * 나만의 장소 페이징 x
	 */
	@Override
	public List<MemberMyspotDto> selectMemberMyspotNolimitList(MemberMyspotDefaultDto searchDto) throws Exception {
		QMemberMyspot qMyspot = QMemberMyspot.memberMyspot;
		QMember qMember = QMember.member;
		
		List<MemberMyspot> list = jpaQueryFactory.selectFrom(qMyspot)
				.leftJoin(qMember).fetchJoin()
				.where(commonQuery(searchDto)).fetch();
		
		return list.stream().map(x -> new MemberMyspotDto(x)).collect(Collectors.toList());
	}
}
