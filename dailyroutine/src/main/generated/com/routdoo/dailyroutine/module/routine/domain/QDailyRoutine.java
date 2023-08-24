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

    public static final QDailyRoutine dailyRoutine = new QDailyRoutine("dailyRoutine");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final ListPath<DailyRoutineTimeLine, QDailyRoutineTimeLine> dailyRoutineTimeLine = this.<DailyRoutineTimeLine, QDailyRoutineTimeLine>createList("dailyRoutineTimeLine", DailyRoutineTimeLine.class, QDailyRoutineTimeLine.class, PathInits.DIRECT2);

    public final EnumPath<com.routdoo.dailyroutine.module.routine.service.RoutineDayType> dayType = createEnum("dayType", com.routdoo.dailyroutine.module.routine.service.RoutineDayType.class);

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath startDate = createString("startDate");

    public QDailyRoutine(String variable) {
        super(DailyRoutine.class, forVariable(variable));
    }

    public QDailyRoutine(Path<? extends DailyRoutine> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDailyRoutine(PathMetadata metadata) {
        super(DailyRoutine.class, metadata);
    }

}

