package com.routdoo.dailyroutine.module.routine.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyRoutineReplyComment is a Querydsl query type for DailyRoutineReplyComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyRoutineReplyComment extends EntityPathBase<DailyRoutineReplyComment> {

    private static final long serialVersionUID = 366665358L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyRoutineReplyComment dailyRoutineReplyComment = new QDailyRoutineReplyComment("dailyRoutineReplyComment");

    public final StringPath context = createString("context");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final QDailyRoutine dailyRoutine;

    public final QDailyRoutineComment dailyRoutineComment;

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final com.routdoo.dailyroutine.auth.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public QDailyRoutineReplyComment(String variable) {
        this(DailyRoutineReplyComment.class, forVariable(variable), INITS);
    }

    public QDailyRoutineReplyComment(Path<? extends DailyRoutineReplyComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyRoutineReplyComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyRoutineReplyComment(PathMetadata metadata, PathInits inits) {
        this(DailyRoutineReplyComment.class, metadata, inits);
    }

    public QDailyRoutineReplyComment(Class<? extends DailyRoutineReplyComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dailyRoutine = inits.isInitialized("dailyRoutine") ? new QDailyRoutine(forProperty("dailyRoutine"), inits.get("dailyRoutine")) : null;
        this.dailyRoutineComment = inits.isInitialized("dailyRoutineComment") ? new QDailyRoutineComment(forProperty("dailyRoutineComment"), inits.get("dailyRoutineComment")) : null;
        this.member = inits.isInitialized("member") ? new com.routdoo.dailyroutine.auth.member.domain.QMember(forProperty("member")) : null;
    }

}

