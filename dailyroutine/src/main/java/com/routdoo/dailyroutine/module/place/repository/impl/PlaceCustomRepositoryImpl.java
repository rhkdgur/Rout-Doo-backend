package com.routdoo.dailyroutine.module.place.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.cms.publiccode.domain.QPublicCode;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.place.domain.*;
import com.routdoo.dailyroutine.module.place.dto.*;
import com.routdoo.dailyroutine.module.place.repository.PlaceCustomRepository;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		//댓글 idx
		if(searchDto.getCommentIdx() > 0){
			sql.and(qPlaceComment.idx.eq(searchDto.getCommentIdx()));
		}
		
		return sql;
	}

	@Override
	public Page<PlaceSummaryResponse> selectPlacePageList(PlaceDefaultDto searchDto) throws Exception {
		QPlace qPlace = QPlace.place;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;
		QPublicCode qPublicCode = QPublicCode.publicCode;

		Long cnt = jpaQueryFactory.select(qPlace.count()).from(qPlace)
				.leftJoin(qPlaceComment).on(qPlace.placeNum.eq(qPlaceComment.place.placeNum)).fetchJoin()
				.leftJoin(qPlaceLike).on(qPlace.placeNum.eq(qPlaceLike.place.placeNum)).fetchJoin()
				.where(commonQuery(searchDto))
				.fetchOne();
		
		List<PlaceSummaryResponse> places = jpaQueryFactory.select(
						Projections.constructor(
								PlaceSummaryResponse.class,
								qPlace.placeNum,
								qPlace.title,
								qPlace.tel,
								qPlace.categCd,
								qPublicCode.title,
								qPlace.addr,
								qPlace.mapx,
								qPlace.mapy,
								qPlace.pstatus.stringValue(),
								qPlace.createDate,
								qPlace.modifyDate
						)
				)
					.from(qPlace)
					.leftJoin(qPlaceComment).on(qPlace.placeNum.eq(qPlaceComment.place.placeNum)).fetchJoin()
					.leftJoin(qPlaceLike).on(qPlace.placeNum.eq(qPlaceLike.place.placeNum)).fetchJoin()
				  .leftJoin(qPublicCode).on(qPublicCode.pubCd.eq(qPlace.categCd)).fetchJoin()
					.where(commonQuery(searchDto))
					.offset(searchDto.getPageable().getOffset())
					.limit(searchDto.getPageable().getPageSize())
					.fetch();

		
		return new PageImpl<>(places,searchDto.getPageable(),cnt);
	}

	@Override
	public List<PlaceDto> selectPlaceList(PlaceDefaultDto searchDto) throws Exception {
		
		QPlace qPlace = QPlace.place;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;
		QPublicCode qPublicCode = QPublicCode.publicCode;
		List<PlaceDto> placesList = new ArrayList<PlaceDto>();
		
		List<Tuple> places = jpaQueryFactory.select(
						qPlace.placeNum,
						qPlace.title,
						qPlace.tel,
						qPlace.categCd,
						qPublicCode.title,
						qPlace.addr,
						qPlace.mapx,
						qPlace.mapy,
						qPlace.pstatus.stringValue(),
						qPlace.createDate,
						qPlace.modifyDate
				).from(qPlace)
				.leftJoin(qPlaceComment).on(qPlace.placeNum.eq(qPlaceComment.place.placeNum)).fetchJoin()
				.leftJoin(qPlaceLike).on(qPlace.placeNum.eq(qPlaceLike.place.placeNum)).fetchJoin()
				.leftJoin(qPublicCode).on(qPublicCode.pubCd.eq(qPlace.categCd)).fetchJoin()
				.where(commonQuery(searchDto))
				.fetch();

		for(Tuple tp : places){
			PlaceDto placeDto = new PlaceDto();
			placeDto.setPlaceNum(tp.get(qPlace.placeNum));
			placeDto.setTitle(tp.get(qPlace.title));
			placeDto.setTel(tp.get(qPlace.tel));
			placeDto.setCategCd(tp.get(qPlace.categCd));
			placeDto.setCategNm(tp.get(qPublicCode.title));
			placeDto.setAddr(tp.get(qPlace.addr));
			placeDto.setMapx(tp.get(qPlace.mapx));
			placeDto.setMapy(tp.get(qPlace.mapy));
			placeDto.setPstatus(tp.get(qPlace.pstatus.stringValue()));
			placeDto.setCreateDate(tp.get(qPlace.createDate));
			placeDto.setModifyDate(tp.get(qPlace.modifyDate));
			placesList.add(placeDto);
		}
		
		return placesList;
	}

	@Override
	public PlaceDto selectPlaceView(PlaceDto dto) throws Exception {
		
		QPlace qPlace = QPlace.place;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;
		QPlaceIntro qPlaceIntro = QPlaceIntro.placeIntro;
		
		Place place = jpaQueryFactory.selectFrom(qPlace)
				.leftJoin(qPlace.placeComments,qPlaceComment).fetchJoin()
				.leftJoin(qPlace.placeLikes,qPlaceLike).fetchJoin()
				.leftJoin(qPlace.placeIntros,qPlaceIntro).fetchJoin()
				.where(new BooleanBuilder().and(qPlace.placeNum.eq(dto.getPlaceNum()))).fetchOne();

		if(place != null) {
			dto.addPlaceSummaryInfo(place);
		}
		
		return dto;
	}

	@Override
	public List<PlaceIntroDto> selectPlaceIntroList(PlaceIntroDto dto) throws Exception {
		QPlaceIntro qPlaceIntro = QPlaceIntro.placeIntro;
		QMember qMember = QMember.member;

		BooleanBuilder sql = new BooleanBuilder();
		if(dto.getPlaceNum() != null && !dto.getPlaceNum().isEmpty()){
			sql.and(qPlaceIntro.place.placeNum.eq(dto.getPlaceNum()));
		}
		if(dto.getMemberId() != null && !dto.getMemberId().isEmpty()){
			sql.and(qPlaceIntro.member.id.eq(dto.getMemberId()));
		}

		List<PlaceIntroDto> resultList = new ArrayList<>();

		List<Tuple> list = jpaQueryFactory.select(
					qPlaceIntro.idx,
				qPlaceIntro.member.id,
				qPlaceIntro.place.placeNum,
				qPlaceIntro.introText,
				qPlaceIntro.visitDate,
				qPlaceIntro.score,
				qPlaceIntro.createDate,
				qPlaceIntro.modifyDate,
				qMember.nickname
				)
				.from(qPlaceIntro)
				.join(qMember).on(qMember.id.eq(qPlaceIntro.member.id)).fetchJoin()
				.where(sql).fetch();

		for(Tuple tuple : list){
			dto = new PlaceIntroDto();
			dto.setIdx(tuple.get(qPlaceIntro.idx));
			dto.setPlaceNum(tuple.get(qPlaceIntro.place.placeNum));
			dto.setMemberId(tuple.get(qPlaceIntro.member.id));
			dto.setIntroText(tuple.get(qPlaceIntro.introText));
			dto.setVisitDate(tuple.get(qPlaceIntro.visitDate));
			dto.setScore(tuple.get(qPlaceIntro.score));
			dto.getMember().setNickname(tuple.get(qMember.nickname));
			resultList.add(dto);
		}

		return resultList;
	}

	@Override
	public boolean updatePlaceIntro(PlaceIntroDto dto) throws Exception {
		QPlaceIntro qPlaceIntro = QPlaceIntro.placeIntro;
		return jpaQueryFactory.update(qPlaceIntro)
				.set(qPlaceIntro.introText,dto.getIntroText())
				.set(qPlaceIntro.visitDate,dto.getVisitDate())
				.set(qPlaceIntro.score,dto.getScore())
				.where(qPlaceIntro.idx.eq(dto.getIdx())).execute() > 0;
	}

	@Override
	public boolean deletePlaceIntro(PlaceIntroDto dto) throws Exception {
		QPlaceIntro qPlaceIntro = QPlaceIntro.placeIntro;
		return jpaQueryFactory.delete(qPlaceIntro)
				.where(new BooleanBuilder().and(qPlaceIntro.member.id.eq(dto.getMemberId())
						.and(qPlaceIntro.idx.eq(dto.getIdx())))).execute() > 0;
	}

	@Override
	public boolean updatePlaceStatus(PlaceDto dto) throws Exception {
		QPlace qPlace = QPlace.place;
		return jpaQueryFactory.update(qPlace)
				.set(qPlace.pstatus,PlaceStatusType.valueOf(dto.getPstatus()))
				.where(qPlace.placeNum.eq(dto.getPlaceNum())).execute() > 0;
	}

	@Override
	public Page<Map<String, Object>> selectMyPlaceLikePageList(PlaceLikeDefaultDto searchDto) throws Exception {

		QPlace qPlace = QPlace.place;
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;

		long cnt = jpaQueryFactory.select(qPlace.count()).from(qPlace)
				.join(qPlaceLike).on(qPlaceLike.place.placeNum.eq(qPlace.placeNum)).fetchJoin()
				.where(qPlaceLike.member.id.eq(searchDto.getMemberId()))
				.fetchFirst();

		List<Place> list = jpaQueryFactory.selectFrom(qPlace)
				.join(qPlaceLike).on(qPlaceLike.place.placeNum.eq(qPlace.placeNum)).fetchJoin()
				.where(qPlaceLike.member.id.eq(searchDto.getMemberId()))
				.offset(searchDto.getPageable().getOffset())
				.limit(searchDto.getPageable().getPageSize())
				.fetch();

		List<Map<String,Object>> placeList = list.stream().map(Place::toSummaryMap).toList();

		return new PageImpl<>(placeList,searchDto.getPageable(),cnt);
	}

	@Override
	public PlaceLikeDto selectPlaceLike(PlaceLikeDto dto) throws Exception {
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;

		PlaceLike placeLike = jpaQueryFactory.selectFrom(qPlaceLike)
				.where(new BooleanBuilder().and(qPlaceLike.idx.eq(dto.getIdx())))
				.fetchFirst();

		if(placeLike == null){
			return null;
		}

		return new PlaceLikeDto(placeLike);
	}

	@Override
	public Long insertPlaceLike(PlaceLikeDto dto) throws Exception {

		int result = entityManager.createNativeQuery("insert into place_like (place_num,member_id,create_date,modify_date) values (?,?,?,?)")
				.setParameter(1,dto.getPlaceNum())
				.setParameter(2,dto.getMemberId())
				.setParameter(3,LocalDateTime.now())
				.setParameter(4,LocalDateTime.now())
				.executeUpdate();

		return (long) result;
	}

	@Override
	public Long deletePlaceLike(PlaceLikeDto dto) throws Exception {
		QPlaceLike qPlaceLike = QPlaceLike.placeLike;
		return jpaQueryFactory.delete(qPlaceLike)
				.where(new BooleanBuilder().and(qPlaceLike.idx.eq(dto.getIdx())))
				.execute();
	}

	@Override
	public Page<PlaceCommentDto> selectPlaceCommentPageList(PlaceDefaultDto searchDto) throws Exception {
		
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		
		Long cnt = jpaQueryFactory.select(qPlaceComment.count()).from(qPlaceComment)
				.where(commonQuery(searchDto)).fetchOne();
		
		List<PlaceComment> placeComments = jpaQueryFactory.selectFrom(qPlaceComment)
							.where(commonQuery(searchDto))
							.offset(searchDto.getPageable().getOffset())
							.limit(searchDto.getPageable().getPageSize())
							.fetch();
		
		return new PageImpl<>(placeComments.stream().map(PlaceCommentDto::new).collect(Collectors.toList()),searchDto.getPageable(),cnt);
	}

	@Override
	public Long insertPlaceComment(PlaceCommentDto dto) throws Exception {

		int result = entityManager.createNativeQuery("insert into place_comment (place_num, member_id, content, create_date,modify_date) values(?,?,?,?,?)")
				.setParameter(1,dto.getPlaceNum())
				.setParameter(2,dto.getMemberDto().getId())
				.setParameter(3,dto.getContent())
				.setParameter(4,LocalDateTime.now())
				.setParameter(5,LocalDateTime.now())
				.executeUpdate() ;

		return (long) result;

	}

	@Override
	public Long updatePlaceComment(PlaceCommentDto dto) throws Exception {
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		return jpaQueryFactory.update(qPlaceComment)
				.set(qPlaceComment.content, dto.getContent())
				.where(qPlaceComment.idx.eq(dto.getIdx())).execute();
	}

	@Override
	public Long deletePlaceComment(PlaceCommentDto dto) throws Exception {

		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QPlaceReplyComment qPlaceReplyComment = QPlaceReplyComment.placeReplyComment;

		//댓글에 달린 답글들 삭제
		Long reply = jpaQueryFactory.delete(qPlaceReplyComment)
				.where(qPlaceReplyComment.placeComment.idx.eq(dto.getIdx())).execute();

		return jpaQueryFactory.delete(qPlaceComment)
				.where(qPlaceComment.idx.eq(dto.getIdx())).execute();
	}

	/**
	 * 댓글에 대한 답글 목록
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PlaceReplyCommentResponse> selectPlaceReplyCommentList(PlaceDefaultDto searchDto) throws Exception {

		QPlaceReplyComment qPlaceReplyComment = QPlaceReplyComment.placeReplyComment;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QMember qMember = QMember.member ;
		QPlace qPlace = QPlace.place;

		List<PlaceReplyCommentResponse> list = jpaQueryFactory.select(
					Projections.constructor(
							PlaceReplyCommentResponse.class,
							qPlaceReplyComment.idx,
							qPlaceComment.idx,
							qPlace.placeNum,
							qPlaceReplyComment.content,
							qMember.id,
							qMember.nickname,
							qPlaceReplyComment.createDate,
							qPlaceReplyComment.modifyDate
					)
				)
				.from(qPlaceReplyComment)
				.join(qPlaceComment).fetchJoin()
				.join(qMember).fetchJoin()
				.join(qPlaceComment).fetchJoin()
				.where(commonQuery(searchDto)).fetch();

		return list;
	}

	/**
	 * 댓글에 대한 답글 단일 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public PlaceReplyCommentDto selectPlaceReplyComment(PlaceReplyCommentDto dto) throws Exception {
		QPlaceReplyComment qPlaceReplyComment = QPlaceReplyComment.placeReplyComment;
		QPlaceComment qPlaceComment = QPlaceComment.placeComment;
		QMember qMember = QMember.member ;
		QPlace qPlace = QPlace.place;

		PlaceReplyComment entity = jpaQueryFactory.selectFrom(qPlaceReplyComment)
				.join(qPlaceComment).fetchJoin()
				.join(qMember).fetchJoin()
				.join(qPlaceComment).fetchJoin()
				.where(qPlaceReplyComment.placeComment.idx.eq(dto.getCommentIdx())).fetchFirst();

		if(entity == null){
			return null;
		}

		return new PlaceReplyCommentDto(entity);
	}

	/**
	 * 답글 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long insertPlaceReplyComment(PlaceReplyCommentDto dto) throws Exception {

		int result = entityManager.createNativeQuery("insert into place_comment_reply(member_id,comment_idx,place_num , content,create_date,modify_date) values (?,?,?,?,?,?)")
				.setParameter(1,dto.getMemberSummaryResponse().getId())
				.setParameter(2,dto.getCommentIdx())
				.setParameter(3,dto.getPlaceNum())
				.setParameter(4,dto.getContent())
				.setParameter(5,LocalDateTime.now())
				.setParameter(6,LocalDateTime.now())
				.executeUpdate() ;

		return (long)result;
	}

	/**
	 * 답글 수정
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long updatePlaceReplyComment(PlaceReplyCommentDto dto) throws Exception {
		QPlaceReplyComment qPlaceReplyComment = QPlaceReplyComment.placeReplyComment;
		return jpaQueryFactory.update(qPlaceReplyComment)
				.set(qPlaceReplyComment.content,dto.getContent())
				.set(qPlaceReplyComment.modifyDate, LocalDateTime.now())
				.where(qPlaceReplyComment.idx.eq(dto.getIdx())).execute();
	}

	/**
	 * 답글 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long deletePlaceReplyComment(PlaceReplyCommentDto dto) throws Exception {
		QPlaceReplyComment qPlaceReplyComment = QPlaceReplyComment.placeReplyComment;
		return jpaQueryFactory.delete(qPlaceReplyComment).where(qPlaceReplyComment.idx.eq(dto.getIdx())).execute();
	}


}
