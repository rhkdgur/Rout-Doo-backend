package com.routdoo.dailyroutine.module.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceScore is a Querydsl query type for PlaceScore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceScore extends EntityPathBase<PlaceScore> {

    private static final long serialVersionUID = 215142844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceScore placeScore = new QPlaceScore("placeScore");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QPlace place;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public QPlaceScore(String variable) {
        this(PlaceScore.class, forVariable(variable), INITS);
    }

    public QPlaceScore(Path<? extends PlaceScore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceScore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceScore(PathMetadata metadata, PathInits inits) {
        this(PlaceScore.class, metadata, inits);
    }

    public QPlaceScore(Class<? extends PlaceScore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

