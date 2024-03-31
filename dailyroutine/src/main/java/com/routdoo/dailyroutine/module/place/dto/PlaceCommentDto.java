package com.routdoo.dailyroutine.module.place.dto;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.module.place.domain.PlaceComment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "장소 댓글 DTO")
@Getter
@Setter
@NoArgsConstructor
public class PlaceCommentDto {

	@Schema(description = "댓글 일련번호", example = "1")
	/**일련번호*/
	private Long idx = 0L;

	@Schema(description = "유저 아이디", example = "rout@naver.com")
	@NotBlank
	/**아이디*/
	private MemberDto memberDto = new MemberDto();

	@Schema(description = "장소번호" , example = "P20231212001")
	@NotBlank
	/**장소 일련번호*/
	private String placeNum;
	
	@Schema(description = "내용", example = "라라라라")
	@NotBlank
	/**내용*/
	private String content;

	@Schema(description = "등록일자", example = "2023-01-01 00:00:00 ")
	/**등록일자*/
	private LocalDateTime createDate;

	@Schema(description = "수정일자", example = "2023-01-01 00:00:00 ")
	/**수정일자*/
	private LocalDateTime modifyDate;
	
	public PlaceComment toEntity() {
		return PlaceComment.builder().dto(this).build();
	}

	public PlaceCommentDto(PlaceComment entity) {
		this.idx = entity.getIdx();
		this.memberDto = new MemberDto(entity.getMember());
		this.placeNum = entity.getPlace().getPlaceNum();
		this.content = entity.getContent();
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
		map.put("content",content);
		map.put("nickname",this.memberDto.getNickname());
		map.put("isUser",isCorrect);
		map.put("createDate", this.createDate);
		map.put("modifyDate", this.modifyDate);

		return map;
	}

}
