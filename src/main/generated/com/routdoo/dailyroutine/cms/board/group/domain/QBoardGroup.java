package com.routdoo.dailyroutine.cms.board.group.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardGroup is a Querydsl query type for BoardGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardGroup extends EntityPathBase<BoardGroup> {

    private static final long serialVersionUID = -847848681L;

    public static final QBoardGroup boardGroup = new QBoardGroup("boardGroup");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath gcode = createString("gcode");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> numFile = createNumber("numFile", Integer.class);

    public final StringPath periodYn = createString("periodYn");

    public final StringPath publicYn = createString("publicYn");

    public final StringPath remark = createString("remark");

    public final StringPath title = createString("title");

    public final StringPath uploadPath = createString("uploadPath");

    public final StringPath useNotice = createString("useNotice");

    public final StringPath usePrivity = createString("usePrivity");

    public final StringPath useYn = createString("useYn");

    public QBoardGroup(String variable) {
        super(BoardGroup.class, forVariable(variable));
    }

    public QBoardGroup(Path<? extends BoardGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardGroup(PathMetadata metadata) {
        super(BoardGroup.class, metadata);
    }

}

