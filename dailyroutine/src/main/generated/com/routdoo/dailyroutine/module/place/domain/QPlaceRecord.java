package com.routdoo.dailyroutine.module.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceRecord is a Querydsl query type for PlaceRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceRecord extends EntityPathBase<PlaceRecord> {

    private static final long serialVersionUID = -1947648409L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceRecord placeRecord = new QPlaceRecord("placeRecord");

    public final StringPath addr = createString("addr");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath detailText = createString("detailText");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath mapx = createString("mapx");

    public final StringPath mapy = createString("mapy");

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final QPlace place;

    public final StringPath tel = createString("tel");

    public final StringPath useInfo = createString("useInfo");

    public final EnumPath<com.routdoo.dailyroutine.module.place.service.PlaceStatusType> useType = createEnum("useType", com.routdoo.dailyroutine.module.place.service.PlaceStatusType.class);

    public QPlaceRecord(String variable) {
        this(PlaceRecord.class, forVariable(variable), INITS);
    }

    public QPlaceRecord(Path<? extends PlaceRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceRecord(PathMetadata metadata, PathInits inits) {
        this(PlaceRecord.class, metadata, inits);
    }

    public QPlaceRecord(Class<? extends PlaceRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place")) : null;
    }

}

