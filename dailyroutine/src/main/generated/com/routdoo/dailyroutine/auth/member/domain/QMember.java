package com.routdoo.dailyroutine.auth.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1070454202L;

    public static final QMember member = new QMember("member1");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath birth = createString("birth");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final ListPath<MemberFriends, QMemberFriends> friendList = this.<MemberFriends, QMemberFriends>createList("friendList", MemberFriends.class, QMemberFriends.class, PathInits.DIRECT2);

    public final StringPath gender = createString("gender");

    public final StringPath id = createString("id");

    public final StringPath mbti = createString("mbti");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath phonenumber = createString("phonenumber");

    public final StringPath pw = createString("pw");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

