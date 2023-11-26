package com.routdoo.dailyroutine.module.routine.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.routine.domain.*;
import com.routdoo.dailyroutine.module.routine.dto.*;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
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
		if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()) {
			
		}
		if(searchDto.getMemberId() != null && !searchDto.getMemberId().isEmpty()) {
			sql.and(qDailyRoutine.member.id.eq(searchDto.getMemberId()));
		}
		if(searchDto.getIdx() != 0L) {
			sql.and(qDailyRoutine.idx.eq(searchDto.getIdx()));
		}		
		if(searchDto.getToDate() != null && !searchDto.getToDate().isEmpty()) {
			sql.and(qDailyRoutine.startDate.goe(searchDto.getToDate()));
		}
		if(searchDto.getPublicYn() != null && !searchDto.getPublicYn().isEmpty()){
			sql.and(qDailyRoutine.publicYn.eq(searchDto.getPublicYn()));
		}
		return sql;
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
		
		Long cnt = jpaQueryFactory.select(qDailyRoutine.count()).from(qDailyRoutine)
				.where(commonQuery(searchDto)).fetchOne();
		
		List<DailyRoutine> list = jpaQueryFactory.selectFrom(qDailyRoutine)
				.where(commonQuery(searchDto))
				.offset(searchDto.getPageable().getOffset())
				.limit(searchDto.getPageable().getPageSize())
				.fetch();
		
		return new PageImpl<>(list.stream().map(x-> new DailyRoutineDto(x)).collect(Collectors.toList()), searchDto.getPageable(),cnt);
	}

	@Override
	public List<DailyRoutineDto> selectDailyRoutineList(DailyRoutineDefaultDto searchDto) {
		QDailyRoutine qDailyRoutine = QDailyRoutine.dailyRoutine;
		List<DailyRoutine> list = jpaQueryFactory.selectFrom(qDailyRoutine)
				.where(commonQuery(searchDto))
				.fetch();
		return list.stream().map(x-> new DailyRoutineDto(x)).collect(Collectors.toList());
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
		return list.stream().map(x-> new DailyRoutineTimeLineDto(x)).collect(Collectors.toList());
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
	public Page<DailyRoutineTimeLineDto> selectDailyRoutineTimePageList(DailyRoutineTimeLineDefaultDto searchDto) {
		QDailyRoutineTimeLine qDailyRoutineTimeLine = QDailyRoutineTimeLine.dailyRoutineTimeLine;
		
		Long cnt = jpaQueryFactory.select(qDailyRoutineTimeLine.count()).from(qDailyRoutineTimeLine)
				.where(commonTimeQuery(searchDto)).fetchOne();
		
		List<DailyRoutineTimeLine> list = jpaQueryFactory.selectFrom(qDailyRoutineTimeLine).
		where(commonTimeQuery(searchDto))
		.offset(searchDto.getPageable().getOffset())
		.limit(searchDto.getPageable().getPageSize())
		.fetch();
		
		return new PageImpl<>(list.stream().map(x-> new DailyRoutineTimeLineDto(x)).collect(Collectors.toList()),
				searchDto.getPageable(),cnt);
	}


	@Override
	public Long insertDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception {
		QDailyRoutineLike qDailyRoutineLike = QDailyRoutineLike.dailyRoutineLike;
		return jpaQueryFactory.insert(qDailyRoutineLike)
				.columns(
						qDailyRoutineLike.dailyRoutine.idx,
						qDailyRoutineLike.member.id,
						qDailyRoutineLike.createDate,
						qDailyRoutineLike.modifyDate
				)
				.values(
						dto.getDailyRoutineDto().getIdx(),
						dto.getMemberId(),
						LocalDateTime.now(),
						LocalDateTime.now()
				)
				.execute();
	}

	@Override
	public Long deleteDailyRoutineLike(DailyRoutineLikeDto dto) throws Exception {
		QDailyRoutineLike qDailyRoutineLike = QDailyRoutineLike.dailyRoutineLike;
		return jpaQueryFactory.delete(qDailyRoutineLike)
				.where(qDailyRoutineLike.idx.eq(dto.getIdx()))
				.execute();
	}

}
