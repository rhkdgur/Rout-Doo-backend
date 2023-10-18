package com.routdoo.dailyroutine.module.routine.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyRoutineInvite is a Querydsl query type for DailyRoutineInvite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyRoutineInvite extends EntityPathBase<DailyRoutineInvite> {

    private static final long serialVersionUID = -735943614L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyRoutineInvite dailyRoutineInvite = new QDailyRoutineInvite("dailyRoutineInvite");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final QDailyRoutine dailyRoutine;

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public QDailyRoutineInvite(String variable) {
        this(DailyRoutineInvite.class, forVariable(variable), INITS);
    }

    public QDailyRoutineInvite(Path<? extends DailyRoutineInvite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyRoutineInvite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyRoutineInvite(PathMetadata metadata, PathInits inits) {
        this(DailyRoutineInvite.class, metadata, inits);
    }

    public QDailyRoutineInvite(Class<? extends DailyRoutineInvite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dailyRoutine = inits.isInitialized("dailyRoutine") ? new QDailyRoutine(forProperty("dailyRoutine")) : null;
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
    }

}

