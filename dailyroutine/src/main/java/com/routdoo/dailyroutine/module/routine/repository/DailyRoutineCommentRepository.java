package com.routdoo.dailyroutine.module.routine.repository;

import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineCommentResponse;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineReplyCommentDto;
import com.routdoo.dailyroutine.module.routine.dto.action.comment.DailyRoutineCommentCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.comment.DailyRoutineCommentDeleteRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.comment.DailyRoutineCommentUpdateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.reply.DailyRoutineReplyCommentCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.reply.DailyRoutineReplyCommentDeleteRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.reply.DailyRoutineReplyCommentUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.repository
 * fileName       : DailyRoutineCommentRepository
 * author         : rhkdg
 * date           : 2023-12-05
 * description    : 공개 일정에 대한 댓글
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-05        rhkdg       최초 생성
 */
public interface DailyRoutineCommentRepository  {

    /**
     * 공개 일정에 대한 댓글 목록(페이징)
     * @param dto
     * @return
     * @throws Exception
     */
    Page<DailyRoutineCommentResponse> selectDailyRoutineCommentPageList(DailyRoutineCommentDefaultDto dto) throws Exception;

    /**
     * 공개 일정에 대한 목록( 페이징 x)
     * @param dto
     * @return
     * @throws Exception
     */
    List<DailyRoutineCommentDto> selectDailyRoutineCommentList(DailyRoutineCommentDefaultDto dto) throws Exception;

    /**
     * 공개 일정에 대한 전체 개수
     * @param dto
     * @return
     * @throws Exception
     */
    long selectDailyRoutineCommentTotalCount(DailyRoutineCommentDefaultDto dto) throws Exception;

    /**
     * 공개 일정에 대한 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    DailyRoutineCommentDto selectDailyRoutineComment(DailyRoutineCommentDto dto) throws Exception;

    /**
     * 공개 일정 댓글 등록
     *
     * @param dto
     * @return
     * @throws Exception
     */
    boolean insertDailyRoutineComment(DailyRoutineCommentCreateRequest dto) throws Exception;

    /**
     * 공개 일정 댓글 수정
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updateDailyRoutineComment(DailyRoutineCommentUpdateRequest dto) throws Exception;

    /**
     * 공개 일정 댓글 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    boolean deleteDailyRoutineComment(DailyRoutineCommentDeleteRequest dto) throws Exception;


    /**
     * 공개 일정 댓글에 대한 답글 목록 페이징 o
     * @param dto
     * @return
     * @throws Exception
     */
    Page<DailyRoutineReplyCommentDto> selectDailyRoutineReplyCommentPageList(DailyRoutineReplyCommentDto dto) throws Exception;

    /**
     * 공개 일정 댓글에 대한 답글 목록 페이징 x
     * @param dto
     * @return
     * @throws Exception
     */
    List<DailyRoutineReplyCommentDto> selectDailyRoutineReplyCommentList(DailyRoutineReplyCommentDto dto) throws Exception;

    /**
     * 공개 일정 댓글에 대한 답글 개수
     * @param dto
     * @return
     * @throws Exception
     */
    long selectDailyRoutineReplyCommentTotalCount(DailyRoutineReplyCommentDto dto) throws Exception;

    /**
     * 공개 일정 댓글에 대한 답글 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    DailyRoutineReplyCommentDto selectDailyRoutineReplyComment(DailyRoutineReplyCommentDto dto) throws Exception;

    /**
     * 공개 일정 댓글에 대한 답글 등록
     * @param dto
     * @return
     * @throws Exception
     */
    boolean insertDailyRoutineReplyComment(DailyRoutineReplyCommentCreateRequest dto) throws Exception;

    /**
     * 공개 일정 댓글에 대한 답글 수정
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updateDailyRoutineReplyComment(DailyRoutineReplyCommentUpdateRequest dto) throws Exception;

    /**
     * 공개 일정 댓글에 대한 답글 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    boolean deleteDailyRoutineReplyComment(DailyRoutineReplyCommentDeleteRequest dto) throws Exception;

    /**
     * 놀거리, 일정 댓글 정보 조회
     * @param paramUtil
     * @return
     * @throws Exception
     */
    Page<Map> selectCommentPageList(Map<String,String> paramUtil) throws Exception;
}
