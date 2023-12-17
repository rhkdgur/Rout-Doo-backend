package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.module.place.domain.PlaceRecordRemove;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceRemoveDto
 * author         : rhkdg
 * date           : 2023-12-14
 * description    : 장소 삭제 내역 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaceRecordRemoveDto {

    /**일련번호*/
    private Long idx;

    /**회원 아이디*/
    private String memberId;

    /**장소번호*/
    private String placeNum;

    /**삭제 요청 사유*/
    private String deleteReason;

    /**리젝 사유**/
    private String rejectReason;

    /**승인여부*/
    private String approveType;

    /**등록일자*/
    private LocalDateTime createDate;

    /**수정일자*/
    private LocalDateTime modifyDate;

    public PlaceRecordRemove toEntity(){
        return PlaceRecordRemove.builder().dto(this).build();
    }

    public PlaceRecordRemoveDto(PlaceRecordRemove entity){
        this.idx = entity.getIdx();
        this.memberId = entity.getMember().getId();
        this.placeNum = entity.getPlace().getPlaceNum();
        this.deleteReason = entity.getDeleteReason();
        this.rejectReason = entity.getRejectReason();
        this.approveType = entity.getApproveType().name();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }
    
}
