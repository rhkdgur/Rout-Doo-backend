package com.routdoo.dailyroutine.auth.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberFriends is a Querydsl query type for MemberFriends
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberFriends extends EntityPathBase<MemberFriends> {

    private static final long serialVersionUID = -1678721937L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberFriends memberFriends = new QMemberFriends("memberFriends");

    public final StringPath blockYn = createString("blockYn");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath invitedId = createString("invitedId");

    public final QMember member;

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public QMemberFriends(String variable) {
        this(MemberFriends.class, forVariable(variable), INITS);
    }

    public QMemberFriends(Path<? extends MemberFriends> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberFriends(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberFriends(PathMetadata metadata, PathInits inits) {
        this(MemberFriends.class, metadata, inits);
    }

    public QMemberFriends(Class<? extends MemberFriends> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

