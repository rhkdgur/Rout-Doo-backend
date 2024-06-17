package com.routdoo.dailyroutine.auth.member.dto;

import com.routdoo.dailyroutine.auth.member.domain.MemberMyspot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member.dto
* @fileName      : MemberMyspotDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.18
* @description   : 
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.18        ghgo       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberMyspotDto{

	private static final long serialVersionUID = 1L;

	/**일련번호*/
	private Long idx;

	/**회원정보*/
	private MemberDto member = new MemberDto();

	/**제목*/
	private String title;

	/**주소*/
	private String addr;
	
	/**카테고리*/
	private String categ;
	
	/**해쉬태그*/
	private String hashTag;
	
	/**내용*/
	private String context;
	
	/**공개여부*/
	private String publicYn;
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;
	
	public MemberMyspot toEntity() {
		return MemberMyspot.builder().dto(this).build();
	}
	
	public MemberMyspotDto(MemberMyspot entity) {
		this.idx = entity.getIdx();
		this.member = new MemberDto(entity.getMember());
		this.title = entity.getTitle();
		this.addr = entity.getAddr();
		this.categ = entity.getCateg();
		this.hashTag = entity.getHashTag();
		this.context = entity.getContext();
		this.publicYn = entity.getPublicYn();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}
}
