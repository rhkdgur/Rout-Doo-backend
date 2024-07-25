package com.routdoo.dailyroutine.cms.board.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -1419199507L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.routdoo.dailyroutine.cms.board.group.domain.QBoardGroup boardGroup;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath delYn = createString("delYn");

    public final StringPath endDate = createString("endDate");

    public final StringPath file1 = createString("file1");

    public final StringPath file1Alt = createString("file1Alt");

    public final StringPath file2 = createString("file2");

    public final StringPath file2Alt = createString("file2Alt");

    public final StringPath file3 = createString("file3");

    public final StringPath file3Alt = createString("file3Alt");

    public final StringPath file4 = createString("file4");

    public final StringPath file4Alt = createString("file4Alt");

    public final StringPath file5 = createString("file5");

    public final StringPath file5Alt = createString("file5Alt");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath noticeYn = createString("noticeYn");

    public final StringPath password = createString("password");

    public final StringPath privityYn = createString("privityYn");

    public final StringPath startDate = createString("startDate");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public final StringPath writeIp = createString("writeIp");

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardGroup = inits.isInitialized("boardGroup") ? new com.routdoo.dailyroutine.cms.board.group.domain.QBoardGroup(forProperty("boardGroup")) : null;
    }

}

