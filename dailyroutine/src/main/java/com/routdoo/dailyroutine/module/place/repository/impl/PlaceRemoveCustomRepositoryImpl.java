package com.routdoo.dailyroutine.module.place.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.place.domain.PlaceRemove;
import com.routdoo.dailyroutine.module.place.domain.QPlace;
import com.routdoo.dailyroutine.module.place.domain.QPlaceRemove;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceRemoveDto;
import com.routdoo.dailyroutine.module.place.repository.PlaceRemoveCustomRepository;
import com.routdoo.dailyroutine.module.place.service.PlaceRemoveType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.repository.impl
 * fileName       : PlaceRemoveCustomRepositoryImpl
 * author         : rhkdg
 * date           : 2023-12-15
 * description    : 삭제 요청 repository impl
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-15        rhkdg       최초 생성
 */
@Repository
public class PlaceRemoveCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements PlaceRemoveCustomRepository {

    private BooleanBuilder commonQuery(PlaceRemoveDto dto) {
        QPlaceRemove qPlaceRemove = QPlaceRemove.placeRemove;
        BooleanBuilder sql = new BooleanBuilder();

        if(dto.getPlaceNum() != null && !dto.getPlaceNum().isEmpty()){
            sql.and(qPlaceRemove.place.placeNum.eq(dto.getPlaceNum()));
        }
        if(dto.getMemberId() != null && !dto.getMemberId().isEmpty()){
            sql.and(qPlaceRemove.member.id.eq(dto.getMemberId()));
        }
        return sql;
    }

    @Override
    public List<PlaceRemoveDto> selectPlaceRemoveList(PlaceRemoveDto dto) throws Exception {
        QPlaceRemove qPlaceRemove = QPlaceRemove.placeRemove;

        List<PlaceRemove> list = jpaQueryFactory.selectFrom(qPlaceRemove)
                .where(commonQuery(dto)).fetch();

        return list.stream().map(PlaceRemoveDto::new).toList();
    }

    @Override
    public PlaceRemoveDto selectPlaceRemove(PlaceRemoveDto dto) throws Exception {
        QPlaceRemove qPlaceRemove = QPlaceRemove.placeRemove;

        PlaceRemove placeRemove = jpaQueryFactory.selectFrom(qPlaceRemove)
                .where(new BooleanBuilder().and(qPlaceRemove.member.id.eq(dto.getMemberId())).and(qPlaceRemove.place.placeNum.eq(dto.getPlaceNum())))
                .fetchFirst();

        if(placeRemove == null){
            return null;
        }

        return new PlaceRemoveDto(placeRemove);
    }

    @Override
    public boolean insertPlaceRemove(PlaceRemoveDto dto) throws Exception {
        QPlaceRemove qPlaceRemove = QPlaceRemove.placeRemove;
        return jpaQueryFactory.insert(qPlaceRemove)
                .columns(
                        qPlaceRemove.member.id,
                        qPlaceRemove.place.placeNum,
                        qPlaceRemove.deleteReason,
                        qPlaceRemove.createDate,
                        qPlaceRemove.modifyDate
                ).values(
                        dto.getMemberId(),
                        dto.getPlaceNum(),
                        dto.getDeleteReason(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).execute() > 0;
    }

    @Override
    public boolean updatePlaceRemove(PlaceRemoveDto dto) throws Exception {
        QPlaceRemove qPlaceRemove = QPlaceRemove.placeRemove;
        return jpaQueryFactory.update(qPlaceRemove)
                .set(qPlaceRemove.deleteReason,dto.getDeleteReason())
                .where(new BooleanBuilder().and(qPlaceRemove.member.id.eq(dto.getMemberId()))
                        .and(qPlaceRemove.place.placeNum.eq(dto.getPlaceNum()))).execute() > 0;
    }

    @Override
    public boolean updatePlaceRemoveApproveType(PlaceRemoveDto dto) throws Exception {
        QPlaceRemove qPlaceRemove = QPlaceRemove.placeRemove;
        return jpaQueryFactory.update(qPlaceRemove)
                .set(qPlaceRemove.rejectReason,dto.getRejectReason())
                .set(qPlaceRemove.approveType, PlaceRemoveType.valueOf(dto.getApproveType()))
                .where(qPlaceRemove.idx.eq(dto.getIdx()))
                .execute() > 0;
    }
}
