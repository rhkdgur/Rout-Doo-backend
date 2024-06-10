package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.common.vo.BaseVo;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineReplyComment;
import com.routdoo.dailyroutine.module.routine.dto.action.reply.DailyRoutineReplyCommentCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.reply.DailyRoutineReplyCommentDeleteRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.reply.DailyRoutineReplyCommentUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineReplyCommentDto
 * author         : rhkdg
 * date           : 2023-12-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-05        rhkdg       최초 생성
 */
@Schema(description = "일정 답글 DTO")
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineReplyCommentDto extends BaseVo {
    
    /**일련번호*/
    @Schema(description = "일련번호")
    private Long idx = 0L;

    /**회원 아이디*/
    @Schema(description = "회원 아이디")
    private String memberId = "";
    
    /**코멘트 일련번호*/
    @Schema(description = "댓글 일련번호")
    private Long commentIdx = 0L;
    
    /**내용*/
    @Schema(description = "내용")
    private String content = "";
    
    /**활성화 여부*/
    private String enableType = "";
    
    /**등록일자*/
    @Schema(description = "등록일자")
    private LocalDateTime createDate;
    
    /**수정일자*/
    @Schema(description = "수정일자")
    private LocalDateTime modifyDate;

    /**회원 정보*/
    @Schema(description = "회원 정보(조회에 사용)")
    private MemberDto memberDto = new MemberDto();

    public DailyRoutineReplyComment toEntity(){
        return DailyRoutineReplyComment.builder().dto(this).build();
    }

    public DailyRoutineReplyCommentDto(DailyRoutineReplyComment entity){
        this.idx = entity.getIdx();
        this.memberId = entity.getMember().getId();
        this.commentIdx = entity.getDailyRoutineComment().getIdx();
        this.content = entity.getContent();
        this.enableType = entity.getEnableType().name();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
        this.memberDto = new MemberDto(entity.getMember());
    }

    /**
     * 등록
     * @param create
     * @return
     */
    public static DailyRoutineReplyCommentDto createOf(DailyRoutineReplyCommentCreateRequest create) {
        DailyRoutineReplyCommentDto request = new DailyRoutineReplyCommentDto();
        request.setCommentIdx(create.getCommentIdx());
        request.setContent(create.getContent());
        return request;
    }

    /**
     * 수정
     * @param update
     * @return
     */
    public static DailyRoutineReplyCommentDto updateOf(DailyRoutineReplyCommentUpdateRequest update) {
        DailyRoutineReplyCommentDto request = new DailyRoutineReplyCommentDto();
        request.setIdx(update.getIdx());
        request.setCommentIdx(update.getCommentIdx());
        request.setContent(update.getContent());
        return request;
    }

    /**
     * 삭제
     * @param delete
     * @return
     */
    public static DailyRoutineReplyCommentDto deleteOf(DailyRoutineReplyCommentDeleteRequest delete) {
        DailyRoutineReplyCommentDto request = new DailyRoutineReplyCommentDto();
        request.setIdx(delete.getIdx());
        return request;
    }

    public boolean addCheckUser(String id){
        return id.equals(this.memberDto.getId());
    }
    
}
