package com.routdoo.dailyroutine.auth.jwt.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJwtTokenEntity is a Querydsl query type for JwtTokenEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJwtTokenEntity extends EntityPathBase<JwtTokenEntity> {

    private static final long serialVersionUID = -1719559454L;

    public static final QJwtTokenEntity jwtTokenEntity = new QJwtTokenEntity("jwtTokenEntity");

    public final StringPath accessToken = createString("accessToken");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath refreshToken = createString("refreshToken");

    public QJwtTokenEntity(String variable) {
        super(JwtTokenEntity.class, forVariable(variable));
    }

    public QJwtTokenEntity(Path<? extends JwtTokenEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJwtTokenEntity(PathMetadata metadata) {
        super(JwtTokenEntity.class, metadata);
    }

}

