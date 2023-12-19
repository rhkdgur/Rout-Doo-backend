package com.routdoo.dailyroutine.cms.publiccode.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.cms.publiccode.domain.PublicCode;
import com.routdoo.dailyroutine.cms.publiccode.domain.QPublicCode;
import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDefaultDto;
import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDto;
import com.routdoo.dailyroutine.cms.publiccode.repository.PublicCodeRepository;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.publiccode.repository.impl
 * fileName       : PublicCodeRepositoryImpl
 * author         : GAMJA
 * date           : 2023/12/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/19        GAMJA       최초 생성
 */
@Repository
public class PublicCodeRepositoryImpl extends BaseAbstractRepositoryImpl implements PublicCodeRepository {

    private BooleanBuilder commonQuery(PublicCodeDefaultDto searchDto) throws Exception {
        BooleanBuilder sql = new BooleanBuilder();
        QPublicCode qPublicCode = QPublicCode.publicCode;

        if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()){

        }
        if(searchDto.getPubCd() != null && !searchDto.getPubCd().isEmpty()){
            sql.and(qPublicCode.pubCd.eq(searchDto.getPubCd()));
        }
        if(searchDto.getParentCd() != null && !searchDto.getParentCd().isEmpty()){
            sql.and(qPublicCode.parentCd.eq(searchDto.getParentCd()));
        }
        if(searchDto.getUseYn() != null && !searchDto.getUseYn().isEmpty()){
            sql.and(qPublicCode.useYn.eq(searchDto.getUseYn()));
        }
        return sql;
    }

    @Override
    public Page<PublicCodeDto> selectPublicCodePageList(PublicCodeDefaultDto searchDto) throws Exception {
        QPublicCode qPublicCode = QPublicCode.publicCode;

        Long cnt = jpaQueryFactory.select(qPublicCode.count())
                .from(qPublicCode)
                .where(commonQuery(searchDto))
                .fetchFirst();

        List<PublicCode> list = jpaQueryFactory.selectFrom(qPublicCode)
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize()).fetch();

        return new PageImpl<>(list.stream().map(PublicCodeDto::new).toList(),searchDto.getPageable(),cnt);
    }

    @Override
    public List<PublicCodeDto> selectPublicCodeList(PublicCodeDefaultDto searchDto) throws Exception {
        QPublicCode qPublicCode = QPublicCode.publicCode;
        List<PublicCode> list = jpaQueryFactory.selectFrom(qPublicCode)
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize()).fetch();

        return list.stream().map(PublicCodeDto::new).toList();
    }

    @Override
    public Long selectPublicCodeTotalCount(PublicCodeDefaultDto searchDto) throws Exception {
        QPublicCode qPublicCode = QPublicCode.publicCode;

        Long cnt = jpaQueryFactory.select(qPublicCode.count())
                .from(qPublicCode)
                .where(commonQuery(searchDto))
                .fetchFirst();
        return cnt;
    }

    @Override
    public boolean insertPubliceCode(PublicCodeDto dto) throws Exception {
        QPublicCode qPublicCode = QPublicCode.publicCode;
        return jpaQueryFactory.insert(qPublicCode)
                .columns(
                        qPublicCode.pubCd,
                        qPublicCode.parentCd,
                        qPublicCode.title,
                        qPublicCode.remark,
                        qPublicCode.etc1,
                        qPublicCode.etc2,
                        qPublicCode.etc3,
                        qPublicCode.useYn,
                        qPublicCode.createDate,
                        qPublicCode.modifyDate
                ).values(
                        dto.getPubCd(),
                        dto.getParentCd(),
                        dto.getTitle(),
                        dto.getRemark(),
                        dto.getEtc1(),
                        dto.getEtc2(),
                        dto.getEtc3(),
                        dto.getUseYn(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).execute() > 0;
    }

    @Override
    public boolean updatePublicCode(PublicCodeDto dto) throws Exception {
        QPublicCode qPublicCode = QPublicCode.publicCode;
        return jpaQueryFactory.update(qPublicCode)
                .set(qPublicCode.title,dto.getTitle())
                .set(qPublicCode.etc1,dto.getEtc1())
                .set(qPublicCode.etc2,dto.getEtc2())
                .set(qPublicCode.etc3,dto.getEtc3())
                .set(qPublicCode.useYn,dto.getUseYn())
                .set(qPublicCode.modifyDate, LocalDateTime.now())
                .where(new BooleanBuilder().and(qPublicCode.pubCd.eq(dto.getPubCd()))).execute() > 0;
    }

    @Override
    public boolean deletePublicCode(PublicCodeDto dto) throws Exception {
        QPublicCode qPublicCode = QPublicCode.publicCode;
        return jpaQueryFactory.delete(qPublicCode)
                .where(new BooleanBuilder().and(qPublicCode.pubCd.eq(dto.getPubCd()))).execute() > 0;
    }
}
