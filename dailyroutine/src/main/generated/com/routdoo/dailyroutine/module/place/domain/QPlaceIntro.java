package com.routdoo.dailyroutine.module.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceIntro is a Querydsl query type for PlaceIntro
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceIntro extends EntityPathBase<PlaceIntro> {

    private static final long serialVersionUID = 206240150L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceIntro placeIntro = new QPlaceIntro("placeIntro");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath introText = createString("introText");

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QPlace place;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final StringPath visitDate = createString("visitDate");

    public QPlaceIntro(String variable) {
        this(PlaceIntro.class, forVariable(variable), INITS);
    }

    public QPlaceIntro(Path<? extends PlaceIntro> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceIntro(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceIntro(PathMetadata metadata, PathInits inits) {
        this(PlaceIntro.class, metadata, inits);
    }

    public QPlaceIntro(Class<? extends PlaceIntro> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

