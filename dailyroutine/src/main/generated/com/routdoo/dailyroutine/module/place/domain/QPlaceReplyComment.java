package com.routdoo.dailyroutine.module.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceReplyComment is a Querydsl query type for PlaceReplyComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceReplyComment extends EntityPathBase<PlaceReplyComment> {

    private static final long serialVersionUID = -1205231189L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceReplyComment placeReplyComment = new QPlaceReplyComment("placeReplyComment");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QPlace place;

    public final QPlaceComment placeComment;

    public QPlaceReplyComment(String variable) {
        this(PlaceReplyComment.class, forVariable(variable), INITS);
    }

    public QPlaceReplyComment(Path<? extends PlaceReplyComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceReplyComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceReplyComment(PathMetadata metadata, PathInits inits) {
        this(PlaceReplyComment.class, metadata, inits);
    }

    public QPlaceReplyComment(Class<? extends PlaceReplyComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
        this.placeComment = inits.isInitialized("placeComment") ? new QPlaceComment(forProperty("placeComment"), inits.get("placeComment")) : null;
    }

}

