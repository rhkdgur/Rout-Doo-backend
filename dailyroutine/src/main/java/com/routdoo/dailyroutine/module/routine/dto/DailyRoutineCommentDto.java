package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.common.vo.BaseVo;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineComment;
import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineReplyComment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "일정 댓글 DTO")
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineCommentDto {
    
    /**일련번호*/
    @Schema(description = "일련번호", defaultValue = "0")
    private Long idx = 0L;
    
    /**회원아이디*/
    @Schema(description = "유저 아이디", defaultValue = "")
    private String memberId = "";
    
    /**일정 일련번호*/
    @Schema(description = "일정 일련번호", defaultValue = "0")
    @NotNull
    private Long dailyIdx = 0L;
    
    /**내용*/
    @Schema(description = "내용", defaultValue = "")
    @NotEmpty
    private String content = "";
    
    /**등록일자*/
    @Schema(description = "등록일자")
    private LocalDateTime createDate;
    
    /**수정일자*/
    @Schema(description = "수정일자")
    private LocalDateTime modifyDate;
    
    /**답글 개수*/
    @Schema(description = "답글 개수(조회에 사용)")
    private int replyCnt = 0;

    /**회원 dto*/
    @Schema(description = "회원정보(조회에 사용)")
    private MemberDto memberDto = new MemberDto();

    public DailyRoutineComment toEntity(){
        return DailyRoutineComment.builder().dto(this).build();
    }

    public DailyRoutineCommentDto(DailyRoutineComment entity){
        this.idx = entity.getIdx();
        this.memberId = entity.getMember().getId();
        this.memberDto = new MemberDto(entity.getMember());
        this.dailyIdx = entity.getDailyRoutine().getIdx();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

    public DailyRoutineCommentDto(long idx, String memberId, long dailyIdx, String nickname, String content, String mbti,
                                  long replyCnt){
        this.idx = idx;
        this.memberId = memberId;
        this.dailyIdx = dailyIdx;
        this.memberDto.setNickname(nickname);
        this.content = content;
        this.memberDto.setMbti(mbti);
        this.replyCnt = (int) replyCnt;
    }

    public Map<String,Object> toSummaryMap(){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("idx",this.idx);
        map.put("memberId",this.memberId);
        map.put("dailyIdx",this.dailyIdx);
        map.put("context",this.content);
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
        map.put("context",this.content);
        map.put("isUser",isCorrect);
        map.put("createDate",this.createDate);
        map.put("modifyDate",this.modifyDate);

        return map;
    }

}
