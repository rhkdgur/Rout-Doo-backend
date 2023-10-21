package com.routdoo.dailyroutine.module.routine.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyRoutine is a Querydsl query type for DailyRoutine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyRoutine extends EntityPathBase<DailyRoutine> {

    private static final long serialVersionUID = 1499090905L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyRoutine dailyRoutine = new QDailyRoutine("dailyRoutine");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final ListPath<DailyRoutineTimeLine, QDailyRoutineTimeLine> dailyRoutineTimeLine = this.<DailyRoutineTimeLine, QDailyRoutineTimeLine>createList("dailyRoutineTimeLine", DailyRoutineTimeLine.class, QDailyRoutineTimeLine.class, PathInits.DIRECT2);

    public final EnumPath<com.routdoo.dailyroutine.module.routine.service.RoutineDayType> dayType = createEnum("dayType", com.routdoo.dailyroutine.module.routine.service.RoutineDayType.class);

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath startDate = createString("startDate");

    public final StringPath title = createString("title");

    public QDailyRoutine(String variable) {
        this(DailyRoutine.class, forVariable(variable), INITS);
    }

    public QDailyRoutine(Path<? extends DailyRoutine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyRoutine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyRoutine(PathMetadata metadata, PathInits inits) {
        this(DailyRoutine.class, metadata, inits);
    }

    public QDailyRoutine(Class<? extends DailyRoutine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
    }

}

