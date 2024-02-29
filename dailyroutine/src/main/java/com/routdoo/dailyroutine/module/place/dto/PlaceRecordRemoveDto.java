package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.PlaceRecordRemove;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@Schema(description = "장소 삭제 내역 DTO")
@Getter
@Setter
@NoArgsConstructor
public class PlaceRecordRemoveDto {

    /**일련번호*/
    @Schema(description = "일련번호", defaultValue = "0", example = "0")
    private Long idx = 0L;

    /**회원 아이디*/
    @Schema(description = "회원 아이디", defaultValue = "", example = "test")
    @NotBlank
    private String memberId = "";

    /**장소번호*/
    @Schema(description = "장소번호", defaultValue = "", example = "P202000001")
    @NotBlank
    private String placeNum = "";

    /**삭제 요청 사유*/
    @Schema(description = "삭제 요청 사유", defaultValue = "", example = "")
    @NotEmpty
    private String deleteReason = "";

    /**리젝 사유**/
    @Schema(description = "리젝 사유", defaultValue = "", example = "")
    @NotEmpty
    private String rejectReason = "";

    /**승인여부*/
    @Schema(description = "승인여부", example = "NONE(요청), OK(승인), REJECT(거절)")
    @NotBlank
    private String approveType = "";

    /**등록일자*/
    @Schema(description = "등록일자")
    private LocalDateTime createDate;

    /**수정일자*/
    @Schema(description = "수정일자")
    private LocalDateTime modifyDate;

    @Schema(description = "회원정보(조회시 사용)")
    private MemberDto memberDto = new MemberDto();

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
