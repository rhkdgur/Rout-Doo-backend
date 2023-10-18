package com.routdoo.dailyroutine.auth.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberMyspot is a Querydsl query type for MemberMyspot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberMyspot extends EntityPathBase<MemberMyspot> {

    private static final long serialVersionUID = 430119572L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberMyspot memberMyspot = new QMemberMyspot("memberMyspot");

    public final StringPath addr = createString("addr");

    public final StringPath categ = createString("categ");

    public final StringPath context = createString("context");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath hashTag = createString("hashTag");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath publicYn = createString("publicYn");

    public final StringPath title = createString("title");

    public QMemberMyspot(String variable) {
        this(MemberMyspot.class, forVariable(variable), INITS);
    }

    public QMemberMyspot(Path<? extends MemberMyspot> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberMyspot(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberMyspot(PathMetadata metadata, PathInits inits) {
        this(MemberMyspot.class, metadata, inits);
    }

    public QMemberMyspot(Class<? extends MemberMyspot> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

