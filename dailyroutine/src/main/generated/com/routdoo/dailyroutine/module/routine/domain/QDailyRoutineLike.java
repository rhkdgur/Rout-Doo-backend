package com.routdoo.dailyroutine.module.routine.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyRoutineLike is a Querydsl query type for DailyRoutineLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyRoutineLike extends EntityPathBase<DailyRoutineLike> {

    private static final long serialVersionUID = -2119114992L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyRoutineLike dailyRoutineLike = new QDailyRoutineLike("dailyRoutineLike");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final QDailyRoutine dailyRoutine;

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public QDailyRoutineLike(String variable) {
        this(DailyRoutineLike.class, forVariable(variable), INITS);
    }

    public QDailyRoutineLike(Path<? extends DailyRoutineLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyRoutineLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyRoutineLike(PathMetadata metadata, PathInits inits) {
        this(DailyRoutineLike.class, metadata, inits);
    }

    public QDailyRoutineLike(Class<? extends DailyRoutineLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dailyRoutine = inits.isInitialized("dailyRoutine") ? new QDailyRoutine(forProperty("dailyRoutine"), inits.get("dailyRoutine")) : null;
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
    }

}

