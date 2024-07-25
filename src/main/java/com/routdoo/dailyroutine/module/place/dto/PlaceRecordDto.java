package com.routdoo.dailyroutine.module.place.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.common.exception.validate.annotation.phone.Phone;
import com.routdoo.dailyroutine.module.place.domain.PlaceRecord;
import com.routdoo.dailyroutine.module.place.dto.action.record.PlaceRecordCreateRequest;
import com.routdoo.dailyroutine.module.place.dto.action.record.PlaceRecordUpdateRequest;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

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
@Schema(description = "장소 정보 수정 제안 DTO")
@Getter
@Setter
@NoArgsConstructor
public class PlaceRecordDto {
    
    /**일련번호*/
    @Schema(description = "장소 정보 수정 일련번호", defaultValue = "0", example = "0")
    private Long idx = 0L;

    /**회원 아이디*/
    @Schema(description = "회원 아이디", defaultValue = "", example = "test")
    @NotBlank
    private String memberId;

    /**일련번호*/
    @Schema(description = "장소번호", defaultValue = "", example = "P2020200001")
    @NotBlank
    private String placeNum;

    /**연락처*/
    @Schema(description = "연락처", defaultValue = "", example = "010-0000-0000")
    @NotEmpty
    @Phone
    private String tel;

    /**주소*/
    @Schema(description = "주소", defaultValue = "", example = "부산 해운대구...")
    @NotBlank
    private String addr;

    /**경도*/
    @Schema(description = "경도", defaultValue = "", example = "31.231231")
    @NotBlank
    private String mapx;

    /**위도*/
    @Schema(description = "위도", defaultValue = "", example = "127.123124")
    @NotBlank
    private String mapy;

    /**이용안내*/
    @Schema(description = "이용안내", defaultValue = "", example = "이용안내")
    @NotEmpty
    private String useInfo;

    /**상세내용*/
    @Schema(description = "상세 내용", defaultValue = "", example = "상세내용")
    @NotEmpty
    private String detailText;
    
    /**사용여부*/
    @Schema(description = "사용여부", defaultValue = "", example = "Y, N")
    @NotBlank
    private String useType;

    /**등록일자**/
    @Schema(description = "등록일자", defaultValue = "", example = "2023-00-00 00:00:00 ")
    private LocalDateTime createDate;

    /**수정일자**/
    @Schema(description = "수정일자", defaultValue = "", example = "2023-00-00 00:00:00 ")
    private LocalDateTime modifyDate;

    private MemberDto member = new MemberDto();

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

    /**
     * 등록 action
     * @param placeRecordActionRequest
     * @return
     */
    public static PlaceRecordDto createOf(PlaceRecordCreateRequest placeRecordActionRequest){
        PlaceRecordDto placeRecordDto = new PlaceRecordDto();
        placeRecordDto.setPlaceNum(placeRecordDto.getPlaceNum());
        placeRecordDto.setTel(placeRecordDto.getTel());
        placeRecordDto.setAddr(placeRecordDto.getAddr());
        placeRecordDto.setMapx(placeRecordDto.getMapx());
        placeRecordDto.setMapy(placeRecordDto.getMapy());
        placeRecordDto.setUseInfo(placeRecordDto.getUseInfo());
        placeRecordDto.setDetailText(placeRecordDto.getDetailText());
        placeRecordDto.setUseType(placeRecordDto.getUseType());
        return placeRecordDto;
    }

    /**
     * 수정 action
     * @param placeRecordActionRequest
     * @return
     */
    public static PlaceRecordDto updateOf(PlaceRecordUpdateRequest placeRecordActionRequest){
        PlaceRecordDto placeRecordDto = new PlaceRecordDto();
        placeRecordDto.setIdx(placeRecordDto.getIdx());
        placeRecordDto.setPlaceNum(placeRecordDto.getPlaceNum());
        placeRecordDto.setTel(placeRecordDto.getTel());
        placeRecordDto.setAddr(placeRecordDto.getAddr());
        placeRecordDto.setMapx(placeRecordDto.getMapx());
        placeRecordDto.setMapy(placeRecordDto.getMapy());
        placeRecordDto.setUseInfo(placeRecordDto.getUseInfo());
        placeRecordDto.setDetailText(placeRecordDto.getDetailText());
        placeRecordDto.setUseType(placeRecordDto.getUseType());
        return placeRecordDto;
    }

    public Map<String,Object> toSummaryMap(){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("idx",this.idx);
        map.put("memberId",this.memberId);
        map.put("placeNum",this.placeNum);
        map.put("tel", this.tel);
        map.put("addr", this.addr);
        map.put("mapx", this.mapx);
        map.put("mapy", this.mapy);
        map.put("useInfo",this.useInfo);
        map.put("detailText",this.detailText);
        map.put("useType", this.useType);
        return map;
    }

    public String getUseTypeDisplay(){
        return PlaceStatusType.valueOf(this.useType).getDisplay();
    }

}
