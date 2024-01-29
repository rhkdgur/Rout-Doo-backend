package com.routdoo.dailyroutine.cms.board.group.repository;

import com.routdoo.dailyroutine.cms.board.group.domain.BoardGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.routdoo.dailyroutine.cms.board.group.repository
 * fileName       : BoardGroupRepository
 * author         : rhkdg
 * date           : 2024-01-30
 * description    : 게시판 그룹 JPA Repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-30        rhkdg       최초 생성
 */
public interface BoardGroupRepository extends  JpaRepository<BoardGroup,String>,BoardGroupCustomRepository {

}
