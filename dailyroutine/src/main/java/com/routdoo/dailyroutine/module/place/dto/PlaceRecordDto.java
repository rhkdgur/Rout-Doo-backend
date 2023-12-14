package com.routdoo.dailyroutine.module.place.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.domain.PlaceRecord;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceRecordDto
 * author         : rhkdg
 * date           : 2023-12-14
 * description    : 장소 정보 수정 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaceRecordDto {
    
    /**일련번호*/
    private Long idx;

    /**회원 아이디*/
    private String memberId;

    /**일련번호*/
    private String placeNum;

    /**연락처*/
    private String tel;

    /**주소*/
    private String addr;

    /**경도*/
    private String mapx;

    /**위도*/
    private String mapy;

    /**이용안내*/
    private String useInfo;

    /**상세내용*/
    private String detailText;
    
    /**사용여부*/
    private String useType;

    /**등록일자**/
    private LocalDateTime createDate;

    /**수정일자**/
    private LocalDateTime modifyDate;

    public PlaceRecord toEntity(){
        return PlaceRecord.builder().dto(this).build();
    }

    public PlaceRecordDto(PlaceRecord entity){
        this.idx = entity.getIdx();
        this.memberId = entity.getMember().getId();
        this.placeNum = entity.getPlace().getPlaceNum();
        this.tel = entity.getTel();
        this.addr = entity.getAddr();
        this.mapx = entity.getMapx();
        this.mapy = entity.getMapy();
        this.useInfo = entity.getUseInfo();
        this.detailText = entity.getDetailText();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
        this.useType = entity.getUseType().name();
    }

    @QueryProjection
    public PlaceRecordDto(Long idx, String memberId, String placeNum, String tel, String addr, String mapx, String mapy, String useInfo, String detailText, PlaceStatusType useType, LocalDateTime createDate, LocalDateTime modifyDate) {
        this.idx = idx;
        this.memberId = memberId;
        this.placeNum = placeNum;
        this.tel = tel;
        this.addr = addr;
        this.mapx = mapx;
        this.mapy = mapy;
        this.useInfo = useInfo;
        this.detailText = detailText;
        this.useType = useType.name();
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public String getUseTypeDisplay(){
        return PlaceStatusType.valueOf(this.useType).getDisplay();
    }

}
