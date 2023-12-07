package com.routdoo.dailyroutine.module.routine.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyRoutineTimeLine is a Querydsl query type for DailyRoutineTimeLine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyRoutineTimeLine extends EntityPathBase<DailyRoutineTimeLine> {

    private static final long serialVersionUID = -1281223846L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyRoutineTimeLine dailyRoutineTimeLine = new QDailyRoutineTimeLine("dailyRoutineTimeLine");

    public final StringPath addr = createString("addr");

    public final StringPath applyDate = createString("applyDate");

    public final StringPath context = createString("context");

    public final NumberPath<Integer> cost = createNumber("cost", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> creatDate = createDateTime("creatDate", java.time.LocalDateTime.class);

    public final QDailyRoutine dailyRoutine;

    public final StringPath ehour = createString("ehour");

    public final StringPath emin = createString("emin");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath mapx = createString("mapx");

    public final StringPath mapy = createString("mapy");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final StringPath placeName = createString("placeName");

    public final StringPath shour = createString("shour");

    public final StringPath smin = createString("smin");

    public final StringPath title = createString("title");

    public final EnumPath<com.routdoo.dailyroutine.module.routine.service.RoutineWriteType> writeType = createEnum("writeType", com.routdoo.dailyroutine.module.routine.service.RoutineWriteType.class);

    public QDailyRoutineTimeLine(String variable) {
        this(DailyRoutineTimeLine.class, forVariable(variable), INITS);
    }

    public QDailyRoutineTimeLine(Path<? extends DailyRoutineTimeLine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyRoutineTimeLine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyRoutineTimeLine(PathMetadata metadata, PathInits inits) {
        this(DailyRoutineTimeLine.class, metadata, inits);
    }

    public QDailyRoutineTimeLine(Class<? extends DailyRoutineTimeLine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dailyRoutine = inits.isInitialized("dailyRoutine") ? new QDailyRoutine(forProperty("dailyRoutine"), inits.get("dailyRoutine")) : null;
    }

}

