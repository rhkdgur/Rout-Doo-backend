package com.routdoo.dailyroutine.module.place.dto;

import java.time.LocalDateTime;

import com.routdoo.dailyroutine.common.vo.BaseVo;
import com.routdoo.dailyroutine.module.place.domain.PlaceLike;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.dto
* @fileName      : PlaceLikeDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.28
* @description   : 장소 좋아요
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.28        ghgo       최초 생성
 */
@Schema(description = "장소 좋아요 DTO")
@Getter
@Setter
@NoArgsConstructor
public class PlaceLikeDto {

	@Schema(description = "장소 좋아요 일련번호", defaultValue = "0" , example = "1")
	private Long idx = 0L;
	
	@Schema(description = "회원 아이디", defaultValue = "", example = "test")
	@NotBlank
	private String memberId = "";

	@Schema(description = "장소번호", example = "P20230202001")
	@NotBlank
	private String placeNum = "";
	
	@Schema(description = "등록일자")
	private LocalDateTime createDate;
	
	@Schema(description = "수정일자")
	private LocalDateTime modifyDate;

	@Schema(description = "장소 정보(조회 정보)")
	private PlaceDto placeDto = new PlaceDto();

	public PlaceLike toEntity() {
		return PlaceLike.builder().dto(this).build();
	}

	public PlaceLikeDto(PlaceLike entity) {
		this.idx = entity.getIdx();
		this.placeDto = new PlaceDto();
		placeDto.addPlaceSummaryInfo(entity.getPlace());
		placeNum = placeDto.getPlaceNum();
		this.memberId = entity.getMember().getId();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}
	
	
}
