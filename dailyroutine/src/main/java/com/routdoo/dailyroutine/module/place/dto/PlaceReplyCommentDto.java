package com.routdoo.dailyroutine.module.place.dto;


import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.PlaceReplyComment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Schema(description = "장소 댓글에 대한 답글 DTO")
@Getter
@Setter
@NoArgsConstructor
public class PlaceReplyCommentDto {

    /**일련번호*/
    @Schema(description = "일련번호",defaultValue = "0")
    private Long idx = 0L;
    
    /**코멘트 일련번호*/
    @Schema(description = "댓글 일련번호" ,defaultValue = "0")
    @NotBlank
    private Long commentIdx = 0L;
    
    /**장소번호*/
    @Schema(description = "장소 번호",defaultValue = "")
    @NotBlank
    private String placeNum = "";

    /**내용*/
    @Schema(description = "내용")
    @NotBlank
    private String content = "";
    
    /**등록일자*/
    @Schema(description = "등록일자")
    private LocalDateTime createDate;
    
    /**수정일자*/
    @Schema(description = "수정일자")
    private LocalDateTime modifyDate;

    /***회원 정보*/
    @Schema(description = "회원 정보(조회에 사용)")
    private MemberDto memberDto = new MemberDto();

    public PlaceReplyComment toEntity(){
        return PlaceReplyComment.builder().dto(this).build();
    }

    public PlaceReplyCommentDto(PlaceReplyComment entity){
        this.idx = entity.getIdx();
        this.commentIdx = entity.getPlaceComment().getIdx();
        this.placeNum = entity.getPlace().getPlaceNum();
        this.memberDto = new MemberDto(entity.getMember());
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }

    /**
     * dto -> summary map
     * @return
     */
    public Map<String,Object> toSummaryMap(String id){

        boolean isCorrect = id.equals(this.memberDto.getId());

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("idx", this.idx);
        map.put("commentIdx",this.commentIdx);
        map.put("placeNum",this.placeNum);
        map.put("content",this.content);
        map.put("nickname",this.memberDto.getNickname());
        map.put("isUser",isCorrect);
        map.put("createDate", this.createDate);
        map.put("modifyDate", this.modifyDate);

        return map;
    }

}
