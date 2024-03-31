package com.routdoo.dailyroutine.module.routine.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyRoutineComment is a Querydsl query type for DailyRoutineComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyRoutineComment extends EntityPathBase<DailyRoutineComment> {

    private static final long serialVersionUID = 1945919558L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyRoutineComment dailyRoutineComment = new QDailyRoutineComment("dailyRoutineComment");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final QDailyRoutine dailyRoutine;

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public QDailyRoutineComment(String variable) {
        this(DailyRoutineComment.class, forVariable(variable), INITS);
    }

    public QDailyRoutineComment(Path<? extends DailyRoutineComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyRoutineComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyRoutineComment(PathMetadata metadata, PathInits inits) {
        this(DailyRoutineComment.class, metadata, inits);
    }

    public QDailyRoutineComment(Class<? extends DailyRoutineComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dailyRoutine = inits.isInitialized("dailyRoutine") ? new QDailyRoutine(forProperty("dailyRoutine"), inits.get("dailyRoutine")) : null;
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
    }

}

