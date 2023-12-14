package com.routdoo.dailyroutine.module.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceRemove is a Querydsl query type for PlaceRemove
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceRemove extends EntityPathBase<PlaceRemove> {

    private static final long serialVersionUID = -1947350374L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceRemove placeRemove = new QPlaceRemove("placeRemove");

    public final EnumPath<com.routdoo.dailyroutine.module.place.service.PlaceRemoveType> approveType = createEnum("approveType", com.routdoo.dailyroutine.module.place.service.PlaceRemoveType.class);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath deleteReason = createString("deleteReason");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QPlace place;

    public final StringPath rejectReason = createString("rejectReason");

    public QPlaceRemove(String variable) {
        this(PlaceRemove.class, forVariable(variable), INITS);
    }

    public QPlaceRemove(Path<? extends PlaceRemove> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceRemove(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceRemove(PathMetadata metadata, PathInits inits) {
        this(PlaceRemove.class, metadata, inits);
    }

    public QPlaceRemove(Class<? extends PlaceRemove> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place")) : null;
    }

}

