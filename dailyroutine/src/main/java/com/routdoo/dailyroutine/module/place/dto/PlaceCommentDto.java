package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.PlaceComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

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
	private MemberDto memberDto = new MemberDto();
	
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
		this.memberDto = new MemberDto(entity.getMember());
		this.placeNum = entity.getPlace().getPlaceNum();
		this.context = entity.getContext();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}

	/**
	 * dto -> summary map
	 * isUser : 해당 댓글이 로그인사람과 작성자가 동일한지 체크
	 * @param id
	 * @return
	 */
	public Map<String,Object> toSummaryMap(String id){

		boolean isCorrect = id.equals(this.memberDto.getId());

		Map<String,Object> map = new LinkedHashMap<>();
		map.put("idx",this.idx);
		map.put("placeNum",this.placeNum);
		map.put("context",context);
		map.put("nickname",this.memberDto.getNickname());
		map.put("isUser",isCorrect);
		map.put("createDate", this.createDate);
		map.put("modifyDate", this.modifyDate);

		return map;
	}

}
