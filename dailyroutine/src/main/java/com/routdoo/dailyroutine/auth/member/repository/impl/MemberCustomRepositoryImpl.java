package com.routdoo.dailyroutine.auth.member.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
		return sql;
	}
	
	
	/**
	 * 회원 목록 , no paging
	 */
	@Override
	public List<MemberDto> selectMemberList(MemberDefaultDto searchDto) throws Exception {
		QMember qMember = QMember.member;
		
		List<Member> list = jpaQueryFactory.selectFrom(qMember).where(commonQuery(searchDto)).fetch();
		
		return list.stream().map(x-> new MemberDto(x)).collect(Collectors.toList());
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
