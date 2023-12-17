package com.routdoo.dailyroutine.module.place.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.place.domain.PlaceRecordRemove;
import com.routdoo.dailyroutine.module.place.domain.QPlaceRecordRemove;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordRemoveDto;
import com.routdoo.dailyroutine.module.place.repository.PlaceRecordRemoveCustomRepository;
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
public class PlaceRemoveCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements PlaceRecordRemoveCustomRepository {

    private BooleanBuilder commonQuery(PlaceRecordRemoveDto dto) {
        QPlaceRecordRemove qPlaceRemove = QPlaceRecordRemove.placeRecordRemove;
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
    public List<PlaceRecordRemoveDto> selectPlaceRemoveList(PlaceRecordRemoveDto dto) throws Exception {
        QPlaceRecordRemove qPlaceRemove = QPlaceRecordRemove.placeRecordRemove;

        List<PlaceRecordRemove> list = jpaQueryFactory.selectFrom(qPlaceRemove)
                .where(commonQuery(dto)).fetch();

        return list.stream().map(PlaceRecordRemoveDto::new).toList();
    }

    @Override
    public PlaceRecordRemoveDto selectPlaceRemove(PlaceRecordRemoveDto dto) throws Exception {
        QPlaceRecordRemove qPlaceRemove = QPlaceRecordRemove.placeRecordRemove;

        PlaceRecordRemove placeRecordRemove = jpaQueryFactory.selectFrom(qPlaceRemove)
                .where(new BooleanBuilder().and(qPlaceRemove.member.id.eq(dto.getMemberId())).and(qPlaceRemove.place.placeNum.eq(dto.getPlaceNum())))
                .fetchFirst();

        if(placeRecordRemove == null){
            return null;
        }

        return new PlaceRecordRemoveDto(placeRecordRemove);
    }

    @Override
    public boolean insertPlaceRemove(PlaceRecordRemoveDto dto) throws Exception {
        QPlaceRecordRemove qPlaceRemove = QPlaceRecordRemove.placeRecordRemove;
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
    public boolean updatePlaceRemove(PlaceRecordRemoveDto dto) throws Exception {
        QPlaceRecordRemove qPlaceRemove = QPlaceRecordRemove.placeRecordRemove;
        return jpaQueryFactory.update(qPlaceRemove)
                .set(qPlaceRemove.deleteReason,dto.getDeleteReason())
                .where(new BooleanBuilder().and(qPlaceRemove.member.id.eq(dto.getMemberId()))
                        .and(qPlaceRemove.place.placeNum.eq(dto.getPlaceNum()))).execute() > 0;
    }

    @Override
    public boolean updatePlaceRemoveApproveType(PlaceRecordRemoveDto dto) throws Exception {
        QPlaceRecordRemove qPlaceRemove = QPlaceRecordRemove.placeRecordRemove;
        return jpaQueryFactory.update(qPlaceRemove)
                .set(qPlaceRemove.rejectReason,dto.getRejectReason())
                .set(qPlaceRemove.approveType, PlaceRemoveType.valueOf(dto.getApproveType()))
                .where(qPlaceRemove.idx.eq(dto.getIdx()))
                .execute() > 0;
    }

    @Override
    public boolean deletePlaceRemove(PlaceRecordRemoveDto dto) throws Exception {
        QPlaceRecordRemove qPlaceRecordRemove = QPlaceRecordRemove.placeRecordRemove;
        return jpaQueryFactory.delete(qPlaceRecordRemove)
                .where(qPlaceRecordRemove.idx.eq(dto.getIdx()).and(qPlaceRecordRemove.member.id
                        .eq(dto.getMemberId()))).execute() > 0;
    }
}
