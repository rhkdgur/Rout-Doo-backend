package com.routdoo.dailyroutine.module.place.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.routdoo.dailyroutine.module.place.dto.QPlaceRecordDto is a Querydsl Projection type for PlaceRecordDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPlaceRecordDto extends ConstructorExpression<PlaceRecordDto> {

    private static final long serialVersionUID = -545556721L;

    public QPlaceRecordDto(com.querydsl.core.types.Expression<Long> idx, com.querydsl.core.types.Expression<String> memberId, com.querydsl.core.types.Expression<String> placeNum, com.querydsl.core.types.Expression<String> tel, com.querydsl.core.types.Expression<String> addr, com.querydsl.core.types.Expression<String> mapx, com.querydsl.core.types.Expression<String> mapy, com.querydsl.core.types.Expression<String> useInfo, com.querydsl.core.types.Expression<String> detailText, com.querydsl.core.types.Expression<com.routdoo.dailyroutine.module.place.service.PlaceStatusType> useType, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifyDate) {
        super(PlaceRecordDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, com.routdoo.dailyroutine.module.place.service.PlaceStatusType.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class}, idx, memberId, placeNum, tel, addr, mapx, mapy, useInfo, detailText, useType, createDate, modifyDate);
    }

}

