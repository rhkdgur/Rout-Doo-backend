package com.routdoo.dailyroutine.module.routine.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.routine.domain.*;
import com.routdoo.dailyroutine.module.routine.dto.*;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineCustomRepository;
import com.routdoo.dailyroutine.module.routine.service.RoutineRangeConfigType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.repository.impl
* @fileName      : DailyRoutineCustomRepositoryImpl.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.16
* @description   : 스케줄(일상) custom repostiroy impl
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.16        ghgo       최초 생성
 */
@Repository
public class DailyRoutineCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements DailyRoutineCustomRepository{

	private BooleanBuilder commonQuery(DailyRoutineDefaultDto searchDto) {
		BooleanBuilder sql = new BooleanBuilder();
		QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;

		if (searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()) {
			//커뮤니티 검색
			if (searchDto.isCummunity() && !searchDto.isFullTextSearch()) {
				sql.and(
						qDailyRoutine.title.like("%" + searchDto.getSstring() + "%")
								.or(qDailyRoutine.member.nickname.like("%" + searchDto.getSstring() + "%"))
								.or(qDailyRoutine.tag.like("%" + searchDto.getSstring() + "%"))
				);
			}
		}
		if (searchDto.getMemberId() != null && !searchDto.getMemberId().isEmpty()) {
			sql.and(qDailyRoutine.member.id.eq(searchDto.getMemberId()));
		}
		if (searchDto.getIdx() != 0L) {
			sql.and(qDailyRoutine.idx.eq(searchDto.getIdx()));
		}
		if (searchDto.getToDate() != null && !searchDto.getToDate().isEmpty()) {
			sql.and(qDailyRoutine.startDate.loe(searchDto.getToDate()).and(qDailyRoutine.endDate.goe(searchDto.getToDate())));
		}
		if (searchDto.getRangeType() != null && !searchDto.getRangeType().isEmpty()) {
			sql.and(qDailyRoutine.rangeType.eq(RoutineRangeConfigType.valueOf(searchDto.getRangeType())));
		}
		return sql;
	}

	public BooleanExpression cummunitySearch(StringPath target,StringPath target2, String keyword){
		if(!keyword.isEmpty()) {
			final String formattedSearchWord = "\"" + keyword + "\"";
			return Expressions.numberTemplate(Double.class, "function('match_daily_routine_community', {0},{1},{2})",
					target,target2, formattedSearchWord).gt(0);
		}else{
			return null;
		}
	}

    private OrderSpecifier[] commonOrderBy(DailyRoutineDefaultDto searchDto) {
        List<OrderSpecifier> orderSpecifierList  = new ArrayList<>();
        QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;
        //공개 커뮤니티 일경우 정렬 처리
        if(searchDto.isCummunity()){
            orderSpecifierList.add(new OrderSpecifier(Order.DESC,qDailyRoutine.createDate));
        }else{
            orderSpecifierList.add(new OrderSpecifier(Order.DESC,qDailyRoutine.idx));
        }
        return orderSpecifierList.toArray(new OrderSpecifier[orderSpecifierList.size()]);
    }
	
	private BooleanBuilder commonTimeQuery(DailyRoutineTimeLineDefaultDto searchDto) {
		BooleanBuilder sql = new BooleanBuilder();
		QDailyRoutineTimeLine qDailyRoutineTimeLine = QDailyRoutineTimeLine.dailyRoutineTimeLine;
		
		if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()) {
			
		}
		if(searchDto.getIdx() != 0) {
			sql.and(qDailyRoutineTimeLine.idx.eq(searchDto.getIdx()));
		}	
		if(searchDto.getDailyIdx() != 0) {
			sql.and(qDailyRoutineTimeLine.dailyRoutine.idx.eq(searchDto.getDailyIdx()));
		}
		
		return sql;
	}
	

	@Override
	public Page<DailyRoutineDto> selectDailyRoutinePageList(DailyRoutineDefaultDto searchDto) {
		QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;
		QMember qMember = QMember.member;
		long cnt = 0L;
		cnt = jpaQueryFactory.select(qDailyRoutine.count()).from(qDailyRoutine)
				.where(cummunitySearch(qDailyRoutine.title,qDailyRoutine.tag,searchDto.getSstring()),commonQuery(searchDto)).fetchFirst();
		
		List<Tuple> list = jpaQueryFactory
				.select(qDailyRoutine.idx,
						qDailyRoutine.title,
						qDailyRoutine.tag,
						qMember.id,
						qMember.nickname,
						qDailyRoutine.startDate,
						qDailyRoutine.endDate,
						qDailyRoutine.dayType.stringValue(),
						qDailyRoutine.rangeType.stringValue(),
						qDailyRoutine.createDate,
						qDailyRoutine.modifyDate)
				.from(qDailyRoutine)
				.leftJoin(qDailyRoutine.member,qMember)
				.where(cummunitySearch(qDailyRoutine.title,qDailyRoutine.tag,searchDto.getSstring()),commonQuery(searchDto))
				.offset(searchDto.getPageable().getOffset())
                .orderBy(commonOrderBy(searchDto))
				.limit(searchDto.getPageable().getPageSize())
				.fetch();

		List<DailyRoutineDto> dtos = new ArrayList<>();


		for(Tuple tuple : list){
			DailyRoutineDto dto = new DailyRoutineDto();
			dto.setIdx(tuple.get(qDailyRoutine.idx));
			dto.setTitle(tuple.get(qDailyRoutine.title));
			dto.setTag(tuple.get(qDailyRoutine.tag));
			dto.setMemberId(tuple.get(qMember.id));
			dto.setNickname(tuple.get(qMember.nickname));
			dto.setStartDate(tuple.get(qDailyRoutine.startDate));
			dto.setEndDate(tuple.get(qDailyRoutine.endDate));
			dto.setDayType(tuple.get(qDailyRoutine.dayType.stringValue()));
			dto.setRangeType(tuple.get(qDailyRoutine.rangeType.stringValue()));
			dto.setCreateDate(tuple.get(qDailyRoutine.createDate));
			dto.setModifyDate(tuple.get(qDailyRoutine.modifyDate));
			dtos.add(dto);
		}
		
		return new PageImpl<>(dtos, searchDto.getPageable(),cnt);
	}

	@Override
	public List<DailyRoutineDto> selectDailyRoutineList(DailyRoutineDefaultDto searchDto) {
		QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;
		QMember qMember = QMember.member;

		List<Tuple> list = jpaQueryFactory
				.select(qDailyRoutine.idx,
						qDailyRoutine.title,
						qDailyRoutine.tag,
						qMember.id,
						qMember.nickname,
						qDailyRoutine.startDate,
						qDailyRoutine.endDate,
						qDailyRoutine.dayType.stringValue(),
						qDailyRoutine.rangeType.stringValue(),
						qDailyRoutine.createDate,
						qDailyRoutine.modifyDate)
				.from(qDailyRoutine)
				.leftJoin(qDailyRoutine.member,qMember)
				.where(commonQuery(searchDto))
				.fetch();

		List<DailyRoutineDto> dtos = new ArrayList<>();

		for(Tuple tuple : list){
			DailyRoutineDto dto = new DailyRoutineDto();
			dto.setIdx(tuple.get(qDailyRoutine.idx));
			dto.setTitle(tuple.get(qDailyRoutine.title));
			dto.setTag(tuple.get(qDailyRoutine.tag));
			dto.setMemberId(tuple.get(qMember.id));
			dto.setNickname(tuple.get(qMember.nickname));
			dto.setStartDate(tuple.get(qDailyRoutine.startDate));
			dto.setEndDate(tuple.get(qDailyRoutine.endDate));
			dto.setDayType(tuple.get(qDailyRoutine.dayType.stringValue()));
			dto.setRangeType(tuple.get(qDailyRoutine.rangeType.stringValue()));
			dto.setCreateDate(tuple.get(qDailyRoutine.createDate));
			dto.setModifyDate(tuple.get(qDailyRoutine.modifyDate));
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public Long selectDailyRoutineTotalCount(DailyRoutineDefaultDto searchDto) {
		QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;
		Long cnt = jpaQueryFactory.select(qDailyRoutine.count()).from(qDailyRoutine)
				.where(commonQuery(searchDto)).fetchOne();
		return cnt;
	}

	@Override
	public List<DailyRoutineTimeLineDto> selectDailyRoutineTimeLineList(DailyRoutineTimeLineDefaultDto searchDto) {
		QDailyRoutineTimeLine qDailyRoutineTimeLine = QDailyRoutineTimeLine.dailyRoutineTimeLine;
		List<DailyRoutineTimeLine> list = jpaQueryFactory.selectFrom(qDailyRoutineTimeLine).
				where(commonTimeQuery(searchDto)).fetch();
		return list.stream().map(DailyRoutineTimeLineDto::new).collect(Collectors.toList());
	}

	@Override
	public boolean updateDailyRoutineRangeChange(DailyRoutineDto dailyRoutineDto) throws Exception {
		QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;
		return jpaQueryFactory.update(qDailyRoutine)
				.set(qDailyRoutine.rangeType, RoutineRangeConfigType.valueOf(dailyRoutineDto.getRangeType()))
				.where(new BooleanBuilder().and(qDailyRoutine.idx.eq(dailyRoutineDto.getIdx()))).execute() > 0;
	}

	@Override
	public Page<DailyRoutineTimeLineDto> selectDailyRoutineTimePageList(DailyRoutineTimeLineDefaultDto searchDto) {
		QDailyRoutineTimeLine qDailyRoutineTimeLine = QDailyRoutineTimeLine.dailyRoutineTimeLine;
		
		Long cnt = jpaQueryFactory.select(qDailyRoutineTimeLine.count()).from(qDailyRoutineTimeLine)
				.where(commonTimeQuery(searchDto)).fetchOne();
		
		List<DailyRoutineTimeLine> list = jpaQueryFactory.selectFrom(qDailyRoutineTimeLine).
		where(commonTimeQuery(searchDto))
		.offset(searchDto.getPageable().getOffset())
		.limit(searchDto.getPageable().getPageSize())
		.fetch();
		
		return new PageImpl<>(list.stream().map(DailyRoutineTimeLineDto::new).collect(Collectors.toList()),
				searchDto.getPageable(),cnt);
	}


	@Override
	public Page<DailyRoutineDto> selectDailyRoutineLikePageList(DailyRoutineLikeDefaultDto searchDto) throws Exception {
		QDailyRoutineLike qDailyRoutineLike = QDailyRoutineLike.dailyRoutineLike;
		QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;

		List<DailyRoutine> list = jpaQueryFactory.selectFrom(qDailyRoutine)
				.join(qDailyRoutineLike).on(qDailyRoutineLike.dailyRoutine.idx.eq(qDailyRoutine.idx)).fetchJoin()
				.where(qDailyRoutineLike.member.id.eq(searchDto.getMemberId()))
				.offset(searchDto.getPageable().getOffset())
				.limit(searchDto.getPageable().getPageSize())
				.fetch();

		long cnt = jpaQueryFactory.select(qDailyRoutine.count())
				.from(qDailyRoutine)
				.join(qDailyRoutineLike).on(qDailyRoutineLike.dailyRoutine.idx.eq(qDailyRoutine.idx)).fetchJoin()
				.where(qDailyRoutineLike.member.id.eq(searchDto.getMemberId()))
				.fetchFirst();

		return new PageImpl<>(list.stream().map(DailyRoutineDto::new).toList(),searchDto.getPageable(),cnt);
	}

	@Override
	public DailyRoutineLikeDto selectDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception {
		QDailyRoutineLike qDailyRoutineLike = QDailyRoutineLike.dailyRoutineLike;
		DailyRoutineLike dailyRoutineLike = jpaQueryFactory.selectFrom(qDailyRoutineLike)
				.where(new BooleanBuilder().and(qDailyRoutineLike.idx.eq(dto.getIdx()))).fetchFirst();

		if(dailyRoutineLike == null){
			return null;
		}
		return new DailyRoutineLikeDto(dailyRoutineLike);
	}


	@Override
	public boolean insertDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception {

		return  entityManager.createNativeQuery("insert into daily_routine_like (daily_idx,member_id,create_date,modify_date) values (?,?,?,?)")
				.setParameter(1,dto.getDailyIdx())
				.setParameter(2,dto.getMemberId())
				.setParameter(3,LocalDateTime.now())
				.setParameter(4,LocalDateTime.now())
				.executeUpdate() > 0;
	}

	@Override
	public Long deleteDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception {
		QDailyRoutineLike qDailyRoutineLike = QDailyRoutineLike.dailyRoutineLike;
		return jpaQueryFactory.delete(qDailyRoutineLike)
				.where(qDailyRoutineLike.idx.eq(dto.getIdx()))
				.execute();
	}

	@Override
	public boolean updateDailyRoutinePublicYn(DailyRoutineDto dto) throws Exception {
		QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;
		return jpaQueryFactory.update(qDailyRoutine).
				set(qDailyRoutine.rangeType,RoutineRangeConfigType.valueOf(dto.getRangeType()))
				.where(qDailyRoutine.idx.eq(dto.getIdx()))
				.execute() > 0;
	}

}
