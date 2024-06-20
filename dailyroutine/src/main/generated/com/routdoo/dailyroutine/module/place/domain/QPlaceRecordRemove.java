package com.routdoo.dailyroutine.module.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceRecordRemove is a Querydsl query type for PlaceRecordRemove
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceRecordRemove extends EntityPathBase<PlaceRecordRemove> {

    private static final long serialVersionUID = -707495477L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceRecordRemove placeRecordRemove = new QPlaceRecordRemove("placeRecordRemove");

    public final EnumPath<com.routdoo.dailyroutine.module.place.service.PlaceRemoveType> approveType = createEnum("approveType", com.routdoo.dailyroutine.module.place.service.PlaceRemoveType.class);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath deleteReason = createString("deleteReason");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QPlace place;

    public final StringPath rejectReason = createString("rejectReason");

    public QPlaceRecordRemove(String variable) {
        this(PlaceRecordRemove.class, forVariable(variable), INITS);
    }

    public QPlaceRecordRemove(Path<? extends PlaceRecordRemove> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceRecordRemove(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceRecordRemove(PathMetadata metadata, PathInits inits) {
        this(PlaceRecordRemove.class, metadata, inits);
    }

    public QPlaceRecordRemove(Class<? extends PlaceRecordRemove> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

