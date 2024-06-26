package com.routdoo.dailyroutine.module.routine.dto;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineLike;
import com.routdoo.dailyroutine.module.routine.dto.action.like.DailyRoutineLikeCreateRequest;
import com.routdoo.dailyroutine.module.routine.dto.action.like.DailyRoutineLikeDeleteRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineLikeDto
 * author         : rhkdg
 * date           : 2023-11-26
 * description    : 일정 좋아요 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-26        rhkdg       최초 생성
 */
@Schema(description = "일정 좋아요 DTO")
@Getter
@Setter
@NoArgsConstructor
public class DailyRoutineLikeDto {
    
    /**일련번호*/
    @Schema(description = "일련번호", defaultValue = "0")
    private Long idx = 0L;

    @Schema(description = "일정 일련번호" , defaultValue = "0")
    private Long dailyIdx = 0L;

    /**회원 아이디*/
    @Schema( description = "회원아이디", defaultValue = "")
    private String memberId = "";
    
    /**등록일자*/
    @Schema(description = "등록일자")
    private LocalDateTime createDate;

    /**수정일자*/
    @Schema(description = "수정일자")
    private LocalDateTime modifyDate;

    /**일정 일련번호*/
    @Schema(description = "일정 정보(조회에 사용)")
    private DailyRoutineDto dailyRoutineDto = new DailyRoutineDto();

    public DailyRoutineLike toEntity(){
        return DailyRoutineLike.builder().dto(this).build();
    }

    public DailyRoutineLikeDto(DailyRoutineLike entity){
        this.idx = entity.getIdx();
        this.dailyIdx = entity.getDailyRoutine().getIdx();
        this.dailyRoutineDto = new DailyRoutineDto(entity.getDailyRoutine());
        this.memberId = entity.getMember().getId();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

    public static DailyRoutineLikeDto createOf(DailyRoutineLikeCreateRequest dailyRoutineLikeActionRequest){
        DailyRoutineLikeDto request = new DailyRoutineLikeDto();
        request.setDailyIdx(dailyRoutineLikeActionRequest.getDailyIdx());
        return request;
    }

    public static DailyRoutineLikeDto deleteOf(DailyRoutineLikeDeleteRequest dailyRoutineLikeActionRequest){
        DailyRoutineLikeDto request = new DailyRoutineLikeDto();
        request.setIdx(dailyRoutineLikeActionRequest.getIdx());
        return request;
    }

}
