package com.routdoo.dailyroutine.module.place.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.auth.member.domain.QMember;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import com.routdoo.dailyroutine.module.place.domain.PlaceRecord;
import com.routdoo.dailyroutine.module.place.domain.QPlaceRecord;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordDto;
import com.routdoo.dailyroutine.module.place.dto.QPlaceRecordDto;
import com.routdoo.dailyroutine.module.place.repository.PlaceRecordCustomRepository;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.repository.impl
 * fileName       : PlaceRecordCustomRepositoryImpl
 * author         : rhkdg
 * date           : 2023-12-14
 * description    : 장소 정보 수정 repository impl
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        rhkdg       최초 생성
 */
@Repository
public class PlaceRecordCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements PlaceRecordCustomRepository {


    @Override
    public List<PlaceRecordDto> selectPlaceRecordList(PlaceRecordDto placeRecordDto) throws Exception {
        QPlaceRecord qPlaceRecord = QPlaceRecord.placeRecord;

        List<PlaceRecord>  list = jpaQueryFactory.selectFrom(qPlaceRecord)
                .where(new BooleanBuilder().and(qPlaceRecord.place.placeNum.eq(placeRecordDto.getPlaceNum())))
                .fetch();

        return list.stream().map(PlaceRecordDto::new).toList();
    }

    @Override
    public PlaceRecordDto selectPlaceRecord(PlaceRecordDto dto) throws Exception {
        QPlaceRecord qPlaceRecord = QPlaceRecord.placeRecord;
        QMember qMember = QMember.member;
        return jpaQueryFactory.select(
                    new QPlaceRecordDto(
                            qPlaceRecord.idx,
                            qMember.id,
                            qPlaceRecord.place.placeNum,
                            qPlaceRecord.tel,
                            qPlaceRecord.addr,
                            qPlaceRecord.mapx,
                            qPlaceRecord.mapy,
                            qPlaceRecord.useInfo,
                            qPlaceRecord.detailText,
                            qPlaceRecord.useType.as(qPlaceRecord.useType),
                            qPlaceRecord.createDate,
                            qPlaceRecord.modifyDate
                    )
                )
                .from(qPlaceRecord)
                .join(qPlaceRecord.member,qMember).fetchJoin()
                .where(new BooleanBuilder()
                        .and(qPlaceRecord.idx.eq(dto.getIdx()))
                        .and(qPlaceRecord.member.id.eq(dto.getMemberId()))
                )
                .fetchFirst();
    }

    @Override
    public boolean insertPlaceRecord(PlaceRecordDto dto) throws Exception {
        QPlaceRecord qPlaceRecord = QPlaceRecord.placeRecord;

        return jpaQueryFactory.insert(qPlaceRecord)
                .columns(
                        qPlaceRecord.member.id,
                        qPlaceRecord.place.placeNum,
                        qPlaceRecord.tel,
                        qPlaceRecord.addr,
                        qPlaceRecord.mapx,
                        qPlaceRecord.mapy,
                        qPlaceRecord.useInfo,
                        qPlaceRecord.detailText,
                        qPlaceRecord.useType,
                        qPlaceRecord.createDate,
                        qPlaceRecord.modifyDate
                ).values(
                        dto.getMemberId(),
                        dto.getPlaceNum(),
                        dto.getTel(),
                        dto.getAddr(),
                        dto.getMapx(),
                        dto.getMapy(),
                        dto.getUseInfo(),
                        dto.getDetailText(),
                        PlaceStatusType.valueOf(dto.getUseType()),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).execute() > 0;
    }

    @Override
    public boolean updatePlaceRecord(PlaceRecordDto dto) throws Exception {
        QPlaceRecord qPlaceRecord = QPlaceRecord.placeRecord;
        return jpaQueryFactory.update(qPlaceRecord)
                .set(qPlaceRecord.tel,dto.getTel())
                .set(qPlaceRecord.addr,dto.getAddr())
                .set(qPlaceRecord.mapx,dto.getMapx())
                .set(qPlaceRecord.mapy,dto.getMapy())
                .set(qPlaceRecord.useInfo,dto.getUseInfo())
                .set(qPlaceRecord.detailText,dto.getDetailText())
                .where(qPlaceRecord.idx.eq(dto.getIdx())).execute() > 0;

    }

    @Override
    public boolean deletePlaceRecord(PlaceRecordDto dto) throws Exception {
        QPlaceRecord qPlaceRecord = QPlaceRecord.placeRecord;
        return jpaQueryFactory.delete(qPlaceRecord)
                .where(qPlaceRecord.idx.eq(dto.getIdx())).execute() > 0;
    }

    @Override
    public boolean updatePlaceRecordUseType(PlaceRecordDto dto) throws Exception {
        QPlaceRecord qPlaceRecord = QPlaceRecord.placeRecord;
        return jpaQueryFactory.update(qPlaceRecord)
                .set(qPlaceRecord.useType,PlaceStatusType.valueOf(dto.getUseType()))
                .where(qPlaceRecord.idx.eq(dto.getIdx())).execute() > 0;
    }
}
