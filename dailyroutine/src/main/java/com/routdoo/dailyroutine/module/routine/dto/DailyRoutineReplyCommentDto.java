package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.common.vo.BaseVo;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineComment;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineReplyComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

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
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineReplyCommentDto extends BaseVo {
    
    /**일련번호*/
    private Long idx = 0L;

    /**회원 아이디*/
    private String memberId = "";
    
    /**코멘트 일련번호*/
    private Long commentIdx = 0L;
    
    /**내용*/
    private String context = "";
    
    /**등록일자*/
    private LocalDateTime createDate;
    
    /**수정일자*/
    private LocalDateTime modifyDate;

    /**회원 정보*/
    private MemberDto memberDto = new MemberDto();

    public DailyRoutineReplyComment toEntity(){
        return DailyRoutineReplyComment.builder().dto(this).build();
    }

    public DailyRoutineReplyCommentDto(DailyRoutineReplyComment entity){
        this.idx = entity.getIdx();
        this.memberId = entity.getMember().getId();
        this.commentIdx = entity.getDailyRoutineComment().getIdx();
        this.context = entity.getContext();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
        this.memberDto = new MemberDto(entity.getMember());
    }

    public boolean addCheckUser(String id){
        return id.equals(this.memberDto.getId());
    }
    
}
