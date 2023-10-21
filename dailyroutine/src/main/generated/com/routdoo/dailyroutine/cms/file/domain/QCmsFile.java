package com.routdoo.dailyroutine.cms.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmsFile is a Querydsl query type for CmsFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCmsFile extends EntityPathBase<CmsFile> {

    private static final long serialVersionUID = 30217368L;

    public static final QCmsFile cmsFile = new QCmsFile("cmsFile");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath extension = createString("extension");

    public final StringPath fileSize = createString("fileSize");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath originalFileName = createString("originalFileName");

    public final StringPath parentIdx = createString("parentIdx");

    public final StringPath saveFileName = createString("saveFileName");

    public final StringPath tagName = createString("tagName");

    public final StringPath uploadCode = createString("uploadCode");

    public QCmsFile(String variable) {
        super(CmsFile.class, forVariable(variable));
    }

    public QCmsFile(Path<? extends CmsFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmsFile(PathMetadata metadata) {
        super(CmsFile.class, metadata);
    }

}

