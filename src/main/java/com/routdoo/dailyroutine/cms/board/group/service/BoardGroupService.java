package com.routdoo.dailyroutine.cms.board.group.service;

import com.routdoo.dailyroutine.cms.board.group.domain.BoardGroup;
import com.routdoo.dailyroutine.cms.board.group.dto.BoardGroupDefaultDto;
import com.routdoo.dailyroutine.cms.board.group.dto.BoardGroupDto;
import com.routdoo.dailyroutine.cms.board.group.repository.BoardGroupCustomRepository;
import com.routdoo.dailyroutine.cms.board.group.repository.BoardGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.board.group.service
 * fileName       : BoardGroupService
 * author         : rhkdg
 * date           : 2024-01-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-30        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardGroupService {

    private final BoardGroupRepository boardGroupRepository;


    /**
     * 목록 조회 ( 페이징 o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<BoardGroupDto> selectBoardGroupPageList(BoardGroupDefaultDto searchDto) throws Exception {
        return boardGroupRepository.selectBoardGroupPageList(searchDto);
    }

    /**
     * 목록 조회( 페이징 x)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<BoardGroupDto> selectBoardGroupList(BoardGroupDefaultDto searchDto) throws Exception {
        return boardGroupRepository.selectBoardGroupList(searchDto);
    }

    /**
     * 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    public BoardGroupDto selectBoardGroup(BoardGroupDto dto) throws Exception {
        BoardGroup boardGroup = boardGroupRepository.findById(dto.getGcode()).orElse(null);
        if(boardGroup != null){
            return new BoardGroupDto(boardGroup);
        }
        return null;
    }

    /**
     * 등록,수정
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void saveBoardGroup(BoardGroupDto dto) throws Exception {
        boardGroupRepository.save(dto.toEntity());
    }

    /**
     * 삭제
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void deleteBoardGroup(BoardGroupDto dto) throws Exception {
        boardGroupRepository.deleteById(dto.getGcode());
    }
    
}
