package com.routdoo.dailyroutine.module.place.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.domain.PlaceComment;
import com.routdoo.dailyroutine.module.place.domain.QPlace;
import com.routdoo.dailyroutine.module.place.domain.QPlaceComment;
import com.routdoo.dailyroutine.module.place.domain.QPlaceLike;
import com.routdoo.dailyroutine.module.place.dto.PlaceCommentDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.place.repository.PlaceCustomRepository;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.repository.impl
* @fileName      : PlaceCustomRepositoryServiceImpl.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.27
* @description   : 장소 custom repository impl
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.27        ghgo       최초 생성
 */
@Repository
public class PlaceCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements PlaceCustomRepository{

	/**
	 * 공통 where 쿼리
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	private BooleanBuilder commonQuery(PlaceDefaultDto searchDto) throws Exception {
		BooleanBuilder sql = new BooleanBuilder();
		QPlace qPlace = QPlace.place;
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		
		if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()) {
			
		}
		/**사용상태 조회*/
		if(searchDto.getPstatus() != null && !searchDto.getPstatus().isEmpty()) {
			sql.and(qPlace.pstatus.eq(PlaceStatusType.valueOf(searchDto.getPstatus())));
		}
		if(searchDto.getPlaceNum() != null && !searchDto.getPlaceNum().isEmpty()) {
			sql.and(qPlaceComment.place.placeNum.eq(searchDto.getPlaceNum()));
		}
		
		return sql;
	}

	@Override
	public Page<PlaceDto> selectPlacePageList(PlaceDefaultDto searchDto) throws Exception {
		QPlace qPlace = QPlace.place;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;
		List<PlaceDto> placesList = new ArrayList<PlaceDto>();
		
		Long cnt = jpaQuery.select(qPlace.count()).from(qPlace)
				.leftJoin(qPlaceComment).fetchJoin()
				.leftJoin(qPlaceLike).fetchJoin()
				.where(commonQuery(searchDto))
				.fetchOne();
		
		List<Place> places = jpaQuery.selectFrom(qPlace)
					.leftJoin(qPlaceComment).fetchJoin()
					.leftJoin(qPlaceLike).fetchJoin()
					.where(commonQuery(searchDto))
					.offset(searchDto.getPageable().getOffset())
					.limit(searchDto.getPageable().getPageSize())
					.fetch();
		
		for(Place place : places) {
			PlaceDto dto = new PlaceDto();
			dto.addPlaceSummaryInfo(place);
			placesList.add(dto);
		}
		
		return new PageImpl<>(placesList,searchDto.getPageable(),cnt);
	}

	@Override
	public List<PlaceDto> selectPlaceList(PlaceDefaultDto searchDto) throws Exception {
		
		QPlace qPlace = QPlace.place;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;
		List<PlaceDto> placesList = new ArrayList<PlaceDto>();
		
		List<Place> places = jpaQuery.selectFrom(qPlace)
				.leftJoin(qPlaceComment).fetchJoin()
				.leftJoin(qPlaceLike).fetchJoin()
				.where(commonQuery(searchDto))
				.fetch();
	
		for(Place place : places) {
			PlaceDto dto = new PlaceDto();
			dto.addPlaceSummaryInfo(place);
			placesList.add(dto);
		}
		
		return placesList;
	}

	@Override
	public PlaceDto selectPlaceView(PlaceDto dto) throws Exception {
		
		QPlace qPlace = QPlace.place;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;
		
		Place place = jpaQuery.selectFrom(qPlace)
				.leftJoin(qPlaceComment).fetchJoin()
				.leftJoin(qPlaceLike).fetchJoin()
				.where(new BooleanBuilder().and(qPlace.placeNum.eq(dto.getPlaceNum()))).fetchOne();
		
		dto.addPlaceSummaryInfo(place);
		
		return dto;
	}

	@Override
	public Page<PlaceCommentDto> selectPlaceCommentPageList(PlaceDefaultDto searchDto) throws Exception {
		
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		
		Long cnt = jpaQuery.select(qPlaceComment.count()).from(qPlaceComment)
				.where(commonQuery(searchDto)).fetchOne();
		
		List<PlaceComment> placeComments = jpaQuery.selectFrom(qPlaceComment)
							.where(commonQuery(searchDto))
							.offset(searchDto.getPageable().getOffset())
							.limit(searchDto.getPageable().getPageSize())
							.fetch();
		
		return new PageImpl<>(placeComments.stream().map(PlaceCommentDto::new).collect(Collectors.toList()),searchDto.getPageable(),cnt);
	}
	
}
