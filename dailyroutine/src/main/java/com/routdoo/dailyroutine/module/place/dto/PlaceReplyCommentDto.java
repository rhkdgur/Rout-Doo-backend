package com.routdoo.dailyroutine.module.place.dto;


import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.PlaceReplyComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PlaceReplyCommentDto {

    /**일련번호*/
    private Long idx = 0L;
    
    /**코멘트 일련번호*/
    private Long commentIdx = 0L;
    
    /**장소번호*/
    private String placeNum = "";
    
    /***회원 정보*/
    private MemberDto memberDto = new MemberDto();
    
    /**내용*/
    private String context = "";
    
    /**등록일자*/
    private LocalDateTime createDate;
    
    /**수정일자*/
    private LocalDateTime modifyDate;


    public PlaceReplyComment toEntity(){
        return PlaceReplyComment.builder().dto(this).build();
    }

    public PlaceReplyCommentDto(PlaceReplyComment entity){
        this.idx = entity.getIdx();
        this.commentIdx = entity.getPlaceComment().getIdx();
        this.placeNum = entity.getPlace().getPlaceNum();
        this.memberDto = new MemberDto(entity.getMember());
        this.context = entity.getContext();
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
        map.put("context",this.context);
        map.put("nickname",this.memberDto.getNickname());
        map.put("isUser",isCorrect);
        map.put("createDate", this.createDate);
        map.put("modifyDate", this.modifyDate);

        return map;
    }

}
