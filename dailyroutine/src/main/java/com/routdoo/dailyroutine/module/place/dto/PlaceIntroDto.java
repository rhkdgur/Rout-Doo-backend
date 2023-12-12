package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.domain.PlaceIntro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceIntroDto
 * author         : GAMJA
 * date           : 2023/12/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/13        GAMJA       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaceIntroDto {

    /**일련번호*/
    private Long idx;

    /**장소*/
    private PlaceDto place;

    /**회원*/
    private MemberDto member;

    /**소개글*/
    private String introText;

    /**방문일자*/
    private String visitDate;

    /**별점*/
    private int score = 0;

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
    private LocalDateTime modifyDate;

    public PlaceIntro toEntity(){
        return PlaceIntro.builder().dto(this).build();
    }

    public PlaceIntroDto(PlaceIntro entity){
        this.idx = entity.getIdx();
        this.place = new PlaceDto(entity.getPlace());
        this.member = new MemberDto(entity.getMember());
        this.introText = entity.getIntroText();
        this.visitDate = entity.getVisitDate();
        this.score = entity.getScore();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }
}
