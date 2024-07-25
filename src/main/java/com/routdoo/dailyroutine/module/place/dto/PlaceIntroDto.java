package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileDto;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileSupport;
import com.routdoo.dailyroutine.module.place.domain.PlaceIntro;
import com.routdoo.dailyroutine.module.place.dto.action.intro.PlaceIntroCreateRequest;
import com.routdoo.dailyroutine.module.place.dto.action.intro.PlaceIntroUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class PlaceIntroDto implements CmsFileSupport<CmsFileDto> {

    /**일련번호*/
    private Long idx = 0L;

    /**회원 아이디*/
    private String memberId;
    
    /**장소번호*/
    private String placeNum;

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

    /**장소*/
    private PlaceDto place = new PlaceDto();

    /**회원*/
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

    public static PlaceIntroDto createOf(PlaceIntroCreateRequest request){
        PlaceIntroDto placeIntroDto = new PlaceIntroDto();
        placeIntroDto.setPlaceNum(request.getPlaceNum());
        placeIntroDto.setIntroText(request.getIntroText());
        placeIntroDto.setVisitDate(request.getVisitDate());
        placeIntroDto.setScore(request.getScore());
        return placeIntroDto;
    }

    public static PlaceIntroDto updateOf(PlaceIntroUpdateRequest request){
        PlaceIntroDto placeIntroDto = new PlaceIntroDto();
        placeIntroDto.setIdx(request.getIdx());
        placeIntroDto.setPlaceNum(request.getPlaceNum());
        placeIntroDto.setIntroText(request.getIntroText());
        placeIntroDto.setVisitDate(request.getVisitDate());
        placeIntroDto.setScore(request.getScore());
        return placeIntroDto;
    }

    @Override
    public String getParentIdx() {
        return this.placeNum;
    }

    @Override
    public String getUploadCodePath() {
        return "place/place";
    }

    @Override
    public String getUploadCode() {
        return "upload.place.public";
    }

    private List<CmsFileDto> cmsFileList = new ArrayList<>();

    @Override
    public CmsFileDto[] getCmsFileList() {
        return this.cmsFileList.toArray(new CmsFileDto[this.cmsFileList.size()]);
    }

    @Override
    public void addCmsFileList(CmsFileDto cmsFileDto) {
        this.cmsFileList.add(cmsFileDto);
    }
}
