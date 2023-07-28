package com.routdoo.dailyroutine.module.place.dto;

import java.time.LocalDateTime;

import com.routdoo.dailyroutine.module.place.domain.PlaceComment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.dto
* @fileName      : PlaceCommentDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.28
* @description   : 장소 댓글 
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.28        ghgo       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaceCommentDto {

	/**일련번호*/
	private Long idx;
	
	/**아이디*/
	private String id;
	
	/**장소 일련번호*/
	private String placeNum;
	
	/**내용*/
	private String context;
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;
	
	public PlaceComment toEntity() {
		return PlaceComment.builder().dto(this).build();
	}

	public PlaceCommentDto(PlaceComment entity) {
		this.idx = entity.getIdx();
		this.id = entity.getMember().getId();
		this.placeNum = entity.getPlace().getPlaceNum();
		this.context = entity.getContext();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}
	
}
