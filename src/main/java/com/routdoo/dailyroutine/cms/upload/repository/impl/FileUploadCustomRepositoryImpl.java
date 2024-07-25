package com.routdoo.dailyroutine.cms.upload.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.routdoo.dailyroutine.cms.upload.domain.QFileUpload;
import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDefaultDto;
import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDto;
import com.routdoo.dailyroutine.cms.upload.repository.FileUploadCustomRepository;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.upload.repository.impl
 * fileName       : FileUploadCustomRepositoryImpl
 * author         : rhkdg
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        rhkdg       최초 생성
 */
@Repository
public class FileUploadCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements FileUploadCustomRepository {

    private BooleanBuilder commonQuery(FileUploadDefaultDto searchDto) {
        BooleanBuilder sql = new BooleanBuilder();
        QFileUpload qFileUpload = QFileUpload.fileUpload;

        if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()){
            if(searchDto.getStype().equals("title")){
                sql.and(qFileUpload.title.like("%"+searchDto.getSstring()+"%"));
            }
            if(searchDto.getStype().equals("path")){
                sql.and(qFileUpload.path.like("%"+searchDto.getSstring()+"%"));
            }
        }

        return sql;
    }

    @Override
    public Page<FileUploadDto> selectFileUploadPageList(FileUploadDefaultDto searchDto) {
        QFileUpload qFileUpload =QFileUpload.fileUpload;

        long totCnt = jpaQueryFactory.select(qFileUpload.count())
                .from(qFileUpload)
                .where(commonQuery(searchDto))
                .fetchFirst();

        List<FileUploadDto> list = jpaQueryFactory.select(
                        Projections.constructor(
                            FileUploadDto.class,
                                qFileUpload.code,
                                qFileUpload.title,
                                qFileUpload.shortPath,
                                qFileUpload.path,
                                qFileUpload.useYn
                        )
                )
                .from(qFileUpload)
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list,searchDto.getPageable(),totCnt);
    }
}
