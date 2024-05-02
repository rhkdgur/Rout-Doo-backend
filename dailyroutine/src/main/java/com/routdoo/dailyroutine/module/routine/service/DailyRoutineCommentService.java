package com.routdoo.dailyroutine.module.routine.service;

import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentResponse;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineReplyCommentDto;
import com.routdoo.dailyroutine.module.routine.repository.DailyRoutineCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.service
 * fileName       : DailyRoutineCommentService
 * author         : rhkdg
 * date           : 2023-12-06
 * description    : 플랜리스트 댓글, 답글
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-06        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyRoutineCommentService {

    private final DailyRoutineCommentRepository dailyRoutineCommentRepository;

    /**
     * 플랜 리스트 댓글 페이징
     * @param dto
     * @return
     * @throws Exception
     */
    public Page<DailyRoutineCommentResponse> selectDailyRoutineCommentPageList(DailyRoutineCommentDefaultDto dto) throws Exception {
        return dailyRoutineCommentRepository.selectDailyRoutineCommentPageList(dto);
    }

    /**
     * 댓글 목록 no paging
     * @param dto
     * @return
     * @throws Exception
     */
    public List<DailyRoutineCommentDto> selectDailyRoutineCommentList(DailyRoutineCommentDefaultDto dto) throws Exception {
        return dailyRoutineCommentRepository.selectDailyRoutineCommentList(dto);
    }

    /**
     * 댓글 목록 개수
     * @param dto
     * @return
     * @throws Exception
     */
    public long selectDailyRoutineCommentTotalCount(DailyRoutineCommentDefaultDto dto) throws Exception {
        return dailyRoutineCommentRepository.selectDailyRoutineCommentTotalCount(dto);
    }

    /**
     * 댓글 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    public DailyRoutineCommentDto selectDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.selectDailyRoutineComment(dto);
    }

    /**
     * 댓글 등록
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean insertDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.insertDailyRoutineComment(dto);
    }

    /**
     * 댓글 수정
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updateDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.updateDailyRoutineComment(dto);
    }

    /**
     * 댓글 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean deleteDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.deleteDailyRoutineComment(dto);
    }

    /**
     * 답글 목록 (페이징)
     * @param dto
     * @return
     * @throws Exception
     */
    public Page<DailyRoutineReplyCommentDto> selectDailyRoutineReplyCommentPageList(DailyRoutineReplyCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.selectDailyRoutineReplyCommentPageList(dto);
    }

    /**
     * 답글 목록 no paging
     * @param dto
     * @return
     * @throws Exception
     */
    public List<DailyRoutineReplyCommentDto> selectDailyRoutineReplyCommentList(DailyRoutineReplyCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.selectDailyRoutineReplyCommentList(dto);
    }

    /**
     * 답글 개수
     * @param dto
     * @return
     * @throws Exception
     */
    public long selectDailyRoutineReplyCommentTotalCount(DailyRoutineReplyCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.selectDailyRoutineReplyCommentTotalCount(dto);
    }

    /**
     * 답글 상세 
     * @param dto
     * @return
     * @throws Exception
     */
    public DailyRoutineReplyCommentDto selectDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.selectDailyRoutineReplyComment(dto);
    }

    /**
     * 답글 등록
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean insertDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.insertDailyRoutineReplyComment(dto);
    }

    /**
     * 답글 수정
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updateDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.updateDailyRoutineReplyComment(dto);
    }

    /**
     * 답글 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean deleteDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception {
        return dailyRoutineCommentRepository.deleteDailyRoutineReplyComment(dto);
    }

    /**
     * 일정, 놀거리 댓글 목록 조회
     * @param paramUtil
     * @return
     * @throws Exception
     */
    public Page<Map> selectCommentPageList(Map<String,String> paramUtil) throws Exception {
        return dailyRoutineCommentRepository.selectCommentPageList(paramUtil);
    }
}
