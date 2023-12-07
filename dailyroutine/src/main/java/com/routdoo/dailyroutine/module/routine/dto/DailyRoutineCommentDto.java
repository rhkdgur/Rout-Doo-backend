package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.common.vo.BaseVo;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineComment;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineReplyComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineCommentDto
 * author         : rhkdg
 * date           : 2023-12-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-04        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineCommentDto extends BaseVo {
    
    /**일련번호*/
    private Long idx = 0L;
    
    /**회원아이디*/
    private String memberId = "";
    
    /**일정 일련번호*/
    private Long dailyIdx = 0L;
    
    /**내용*/
    private String context = "";
    
    /**회원 dto*/
    private MemberDto memberDto = new MemberDto();
    
    /**등록일자*/
    private LocalDateTime createDate;
    
    /**수정일자*/
    private LocalDateTime modifyDate;
    
    /**답글 개수*/
    private int replyCnt = 0;

    public DailyRoutineComment toEntity(){
        return DailyRoutineComment.builder().dto(this).build();
    }

    public DailyRoutineCommentDto(DailyRoutineComment entity){
        this.idx = entity.getIdx();
        this.memberId = entity.getMember().getId();
        this.memberDto = new MemberDto(entity.getMember());
        this.dailyIdx = entity.getDailyRoutine().getIdx();
        this.context = entity.getContext();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

    public Map<String,Object> toSummaryMap(){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("idx",this.idx);
        map.put("memberId",this.memberId);
        map.put("dailyIdx",this.dailyIdx);
        map.put("context",this.context);
        map.put("createDate",this.createDate);
        map.put("modifyDate",this.modifyDate);
        return map;
    }

    public Map<String,Object> toSummaryMap(String id){
        Map<String,Object> map = new LinkedHashMap<>();

        boolean isCorrect = id.equals(this.memberDto.getId());

        map.put("idx",this.idx);
        map.put("memberId",this.memberId);
        map.put("dailyIdx",this.dailyIdx);
        map.put("context",this.context);
        map.put("isUser",isCorrect);
        map.put("createDate",this.createDate);
        map.put("modifyDate",this.modifyDate);

        return map;
    }

}
