package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.common.exception.validate.annotation.date.Date;
import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.domain.PlaceIntro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "장소 소개글 DTO")
@Getter
@Setter
@NoArgsConstructor
public class PlaceIntroDto {

    /**일련번호*/
    @Schema(description = "놀거리 소개글 일련번호", example = "1", defaultValue = "0")
    private Long idx = 0L;

    /**회원 아이디*/
    @Schema(description = "회원 아이디", defaultValue = "", example = "test")
    @NotBlank
    private String memberId;
    
    /**장소번호*/
    @Schema(description = "장소 일련번호", defaultValue = "", example = "P202000001")
    @NotBlank
    private String placeNum;

    /**소개글*/
    @Schema(description = "소개글", defaultValue = "", example = "안녕하세요...")
    @NotBlank
    private String introText;

    /**방문일자*/
    @Schema(description = "방문일자", defaultValue = "", example = "2020-00-00")
    @Date
    @NotBlank
    private String visitDate;

    /**별점*/
    @Schema(description = "별점", defaultValue = "0", example = "4")
    private int score = 0;

    /**등록일자*/
    @Schema(description = "등록일자", defaultValue = "null",example = "2020-00-00 00:00:00")
    private LocalDateTime createDate;

    /**수정일자*/
    @Schema(description = "수정일자", defaultValue = "null", example = "2020-00-00 00:00:00 ")
    private LocalDateTime modifyDate;

    /**장소*/
    @Schema(description = "장소 정보(조회에 사용)")
    private PlaceDto place = new PlaceDto();

    /**회원*/
    @Schema(description = "회원 정보(조회에 사용)")
    private MemberDto member = new MemberDto();

    public PlaceIntro toEntity(){
        return PlaceIntro.builder().dto(this).build();
    }

    public PlaceIntroDto(PlaceIntro entity){
        this.idx = entity.getIdx();
        this.placeNum = entity.getPlace().getPlaceNum();
        this.memberId = entity.getMember().getId();
        this.place = new PlaceDto(entity.getPlace());
        this.member = new MemberDto(entity.getMember());
        this.introText = entity.getIntroText();
        this.visitDate = entity.getVisitDate();
        this.score = entity.getScore();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }
}
