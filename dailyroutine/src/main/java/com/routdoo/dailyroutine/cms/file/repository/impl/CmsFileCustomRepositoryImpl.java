package com.routdoo.dailyroutine.cms.file.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.cms.file.domain.CmsFile;
import com.routdoo.dailyroutine.cms.file.domain.QCmsFile;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileDefaultDto;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileDto;
import com.routdoo.dailyroutine.cms.file.repository.CmsFileCustomRepository;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.file.repository.impl
 * fileName       : CmsFileCustomRepositoryImpl
 * author         : GAMJA
 * date           : 2023/12/10
 * description    : 파일 custom repsotiry
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        GAMJA       최초 생성
 */
@Repository
public class CmsFileCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements CmsFileCustomRepository {

    BooleanBuilder commonQuery(CmsFileDefaultDto searchDto) {
        BooleanBuilder sql = new BooleanBuilder();
        QCmsFile qCmsFile = QCmsFile.cmsFile;

        if(searchDto.getIdx() > 0){
            sql.and(qCmsFile.idx.eq(searchDto.getIdx()));
        }
        if(searchDto.getParentIdx() != null && !searchDto.getParentIdx().isEmpty()){
            sql.and(qCmsFile.parentIdx.eq(searchDto.getParentIdx()));
        }
        if(searchDto.getUploadCode() != null && !searchDto.getUploadCode().isEmpty()){
            sql.and(qCmsFile.uploadCode.eq(searchDto.getUploadCode()));
        }

        return sql;
    }

    @Override
    public List<CmsFileDto> selectCmsFileList(CmsFileDefaultDto searchDto) throws Exception {

        QCmsFile qCmsFile = QCmsFile.cmsFile;

        List<CmsFile> list = jpaQueryFactory.selectFrom(qCmsFile)
                .where(commonQuery(searchDto))
                .fetch();

        return list.stream().map(CmsFileDto::new).toList();
    }

    @Override
    public CmsFileDto selectCmsFile(CmsFileDto dto) throws Exception {
        QCmsFile qCmsFile = QCmsFile.cmsFile;

        CmsFile cmsFile = jpaQueryFactory.selectFrom(qCmsFile)
                .where(qCmsFile.idx.eq(dto.getIdx())).fetchFirst();

        if(cmsFile != null){
            return new CmsFileDto(cmsFile);
        }
        return null;
    }

    @Override
    public Long selectCmsFileTotalCount(CmsFileDefaultDto searchDto) throws Exception {
        QCmsFile qCmsFile = QCmsFile.cmsFile;
        return jpaQueryFactory.select(qCmsFile.count())
                .from(qCmsFile)
                .where(commonQuery(searchDto))
                .fetchOne();
    }

    @Override
    public boolean insertCmsFile(CmsFileDto dto) throws Exception {
        QCmsFile qCmsFile = QCmsFile.cmsFile;
        return jpaQueryFactory.insert(qCmsFile)
                .columns(
                        qCmsFile.parentIdx,
                        qCmsFile.uploadCode,
                        qCmsFile.originalFileName,
                        qCmsFile.saveFileName,
                        qCmsFile.fileSize,
                        qCmsFile.extension,
                        qCmsFile.tagName,
                        qCmsFile.createDate,
                        qCmsFile.modifyDate
                ).values(
                        dto.getParentIdx(),
                        dto.getUploadCode(),
                        dto.getOriginalFileName(),
                        dto.getSaveFileName(),
                        dto.getFileSize(),
                        dto.getExtension(),
                        dto.getTagName(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ).execute() > 0;
    }

    @Override
    public boolean updateCmsFile(CmsFileDto dto) throws Exception {
        QCmsFile qCmsFile = QCmsFile.cmsFile;
        return jpaQueryFactory.update(qCmsFile)
                .set(qCmsFile.uploadCode,dto.getUploadCode())
                .set(qCmsFile.originalFileName,dto.getOriginalFileName())
                .set(qCmsFile.saveFileName,dto.getSaveFileName())
                .set(qCmsFile.fileSize,dto.getFileSize())
                .set(qCmsFile.extension,dto.getExtension())
                .set(qCmsFile.tagName,dto.getTagName())
                .where(qCmsFile.idx.eq(dto.getIdx())).execute() > 0;
    }

    @Override
    public boolean deleteCmsFile(CmsFileDto dto) throws Exception {
        QCmsFile qCmsFile = QCmsFile.cmsFile;
        return jpaQueryFactory.delete(qCmsFile)
                .where(qCmsFile.idx.eq(dto.getIdx())).execute() > 0;
    }
}
