package com.routdoo.dailyroutine.cms.upload.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileUpload is a Querydsl query type for FileUpload
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileUpload extends EntityPathBase<FileUpload> {

    private static final long serialVersionUID = -754566257L;

    public static final QFileUpload fileUpload = new QFileUpload("fileUpload");

    public final StringPath code = createString("code");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath path = createString("path");

    public final StringPath shortPath = createString("shortPath");

    public final StringPath title = createString("title");

    public final StringPath useYn = createString("useYn");

    public QFileUpload(String variable) {
        super(FileUpload.class, forVariable(variable));
    }

    public QFileUpload(Path<? extends FileUpload> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileUpload(PathMetadata metadata) {
        super(FileUpload.class, metadata);
    }

}

