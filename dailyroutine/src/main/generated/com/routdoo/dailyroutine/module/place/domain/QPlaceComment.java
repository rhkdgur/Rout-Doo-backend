package com.routdoo.dailyroutine.module.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceComment is a Querydsl query type for PlaceComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceComment extends EntityPathBase<PlaceComment> {

    private static final long serialVersionUID = -379756791L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceComment placeComment = new QPlaceComment("placeComment");

    public final StringPath context = createString("context");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QPlace place;

    public QPlaceComment(String variable) {
        this(PlaceComment.class, forVariable(variable), INITS);
    }

    public QPlaceComment(Path<? extends PlaceComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceComment(PathMetadata metadata, PathInits inits) {
        this(PlaceComment.class, metadata, inits);
    }

    public QPlaceComment(Class<? extends PlaceComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

