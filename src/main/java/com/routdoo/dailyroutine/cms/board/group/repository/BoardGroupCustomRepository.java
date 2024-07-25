package com.routdoo.dailyroutine.cms.board.group.repository;

import com.routdoo.dailyroutine.cms.board.group.dto.BoardGroupDefaultDto;
import com.routdoo.dailyroutine.cms.board.group.dto.BoardGroupDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.board.group.repository
 * fileName       : BoardGroupCustomRepository
 * author         : rhkdg
 * date           : 2024-01-30
 * description    : 게시판 그룹 리포지토리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-30        rhkdg       최초 생성
 */
public interface BoardGroupCustomRepository {

    /**
     * 목록(페이징 o )
     * @param searchDto
     * @return
     */
    Page<BoardGroupDto> selectBoardGroupPageList(BoardGroupDefaultDto searchDto);

    /**
     * 목록(페이징 x )
     * @param searchDto
     * @return
     */
    List<BoardGroupDto> selectBoardGroupList(BoardGroupDefaultDto searchDto);

}
