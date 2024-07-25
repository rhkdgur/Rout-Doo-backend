package com.routdoo.dailyroutine.cms.board.group.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.cms.board.group.domain.BoardGroup;
import com.routdoo.dailyroutine.cms.board.group.domain.QBoardGroup;
import com.routdoo.dailyroutine.cms.board.group.dto.BoardGroupDefaultDto;
import com.routdoo.dailyroutine.cms.board.group.dto.BoardGroupDto;
import com.routdoo.dailyroutine.cms.board.group.repository.BoardGroupCustomRepository;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.routdoo.dailyroutine.cms.board.group.repository.impl
 * fileName       : BoardGroupCustomRepositoryImpl
 * author         : rhkdg
 * date           : 2024-01-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-30        rhkdg       최초 생성
 */
@Repository
public class BoardGroupCustomRepositoryImpl extends BaseAbstractRepositoryImpl implements BoardGroupCustomRepository {

    private BooleanBuilder commonQuery(BoardGroupDefaultDto searchDto){
        QBoardGroup qBoardGroup = QBoardGroup.boardGroup;
        BooleanBuilder sql = new BooleanBuilder();
        if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()){
            if("gcode".equals(searchDto.getStype())){
                sql.and(qBoardGroup.gcode.like("%"+searchDto.getGcode()+"%"));
            }
            if("title".equals(searchDto.getTitle())){
                sql.and(qBoardGroup.title.like("%"+searchDto.getTitle()+"%"));
            }
        }
        if(!searchDto.getPublicYn().isEmpty()){
            sql.and(qBoardGroup.publicYn.eq(searchDto.getPublicYn()));
        }
        if(!searchDto.getUseYn().isEmpty()){
            sql.and(qBoardGroup.useYn.eq(searchDto.getUseYn()));
        }
        return sql;
    }

    @Override
    public Page<BoardGroupDto> selectBoardGroupPageList(BoardGroupDefaultDto searchDto) {
        QBoardGroup qBoardGroup = QBoardGroup.boardGroup;

        Long cnt = jpaQueryFactory.select(qBoardGroup.count())
                .from(qBoardGroup).where(commonQuery(searchDto)).fetchFirst();

        List<BoardGroup> list = jpaQueryFactory.selectFrom(qBoardGroup)
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(list.stream().map(BoardGroupDto::new).toList(),searchDto.getPageable(),cnt);
    }

    @Override
    public List<BoardGroupDto> selectBoardGroupList(BoardGroupDefaultDto searchDto) {
        QBoardGroup qBoardGroup = QBoardGroup.boardGroup;
        List<BoardGroup> list = jpaQueryFactory.selectFrom(qBoardGroup)
                .where(commonQuery(searchDto))
                .offset(searchDto.getPageable().getOffset())
                .limit(searchDto.getPageable().getPageSize())
                .fetch();
        return list.stream().map(BoardGroupDto::new).toList();
    }

}
