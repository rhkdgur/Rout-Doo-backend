package com.routdoo.dailyroutine.module.place.dto;

import java.time.LocalDateTime;

import com.routdoo.dailyroutine.module.place.domain.PlaceLike;

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
@Getter
@Setter
@NoArgsConstructor
public class PlaceLikeDto {

	private Long idx;
	

	private PlaceDto placeDto = new PlaceDto();
	

	private String memberId;
	

	private LocalDateTime createDate;
	

	private LocalDateTime modifyDate;

	public PlaceLike toEntity() {
		return PlaceLike.builder().dto(this).build();
	}

	public PlaceLikeDto(PlaceLike entity) {
		this.idx = entity.getIdx();
		this.placeDto = new PlaceDto();
		placeDto.addPlaceSummaryInfo(entity.getPlace());
		this.memberId = entity.getMember().getId();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}
	
	
}
