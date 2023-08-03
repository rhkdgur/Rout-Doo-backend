package com.routdoo.dailyroutine.module.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlace is a Querydsl query type for Place
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlace extends EntityPathBase<Place> {

    private static final long serialVersionUID = 1615271542L;

    public static final QPlace place = new QPlace("place");

    public final StringPath addr = createString("addr");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath detailText = createString("detailText");

    public final StringPath hashtag = createString("hashtag");

    public final StringPath introText = createString("introText");

    public final StringPath mapx = createString("mapx");

    public final StringPath mapy = createString("mapy");

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final ListPath<PlaceComment, QPlaceComment> placeComments = this.<PlaceComment, QPlaceComment>createList("placeComments", PlaceComment.class, QPlaceComment.class, PathInits.DIRECT2);

    public final ListPath<PlaceLike, QPlaceLike> placeLikes = this.<PlaceLike, QPlaceLike>createList("placeLikes", PlaceLike.class, QPlaceLike.class, PathInits.DIRECT2);

    public final StringPath placeNum = createString("placeNum");

    public final EnumPath<com.routdoo.dailyroutine.module.place.service.PlaceStatusType> pstatus = createEnum("pstatus", com.routdoo.dailyroutine.module.place.service.PlaceStatusType.class);

    public final StringPath title = createString("title");

    public final StringPath useInfo = createString("useInfo");

    public QPlace(String variable) {
        super(Place.class, forVariable(variable));
    }

    public QPlace(Path<? extends Place> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlace(PathMetadata metadata) {
        super(Place.class, metadata);
    }

}

