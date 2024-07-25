package com.routdoo.dailyroutine.cms.publiccode.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPublicCode is a Querydsl query type for PublicCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPublicCode extends EntityPathBase<PublicCode> {

    private static final long serialVersionUID = 848152829L;

    public static final QPublicCode publicCode = new QPublicCode("publicCode");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath etc1 = createString("etc1");

    public final StringPath etc2 = createString("etc2");

    public final StringPath etc3 = createString("etc3");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final StringPath parentCd = createString("parentCd");

    public final StringPath pubCd = createString("pubCd");

    public final StringPath remark = createString("remark");

    public final StringPath title = createString("title");

    public final StringPath useYn = createString("useYn");

    public QPublicCode(String variable) {
        super(PublicCode.class, forVariable(variable));
    }

    public QPublicCode(Path<? extends PublicCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPublicCode(PathMetadata metadata) {
        super(PublicCode.class, metadata);
    }

}

