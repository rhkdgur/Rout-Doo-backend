package com.routdoo.dailyroutine.auth.member.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberMyspotDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member.domain
* @fileName      : MemberMySpot.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.18
* @description   : 나만의 장소 
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.18        ghgo       최초 생성
 */
@Entity
@Table(name="member_myspot")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MemberMyspot{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member = new Member();

	@Comment("제목")
	private String title;

	@Comment("주소")
	private String addr;
	
	@Comment("카테고리")
	private String categ;
	
	@Comment("태그")
	private String hashTag;
	
	@Lob
	@Comment("내용")
	private String context;
	
	@Column(columnDefinition = "char",length = 1)
	@Comment("공개여부")
	private String publicYn;
	
	@CreatedDate
	@Comment("등록일자")
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Comment("수정일자")
	private LocalDateTime modifyDate;

	@Builder
	public MemberMyspot(MemberMyspotDto dto) {
		if(idx > 0) {
			this.idx = dto.getIdx();
		}
		MemberDto m = dto.getMember();
		this.member = Member.builder().dto(m).build();
		this.title = dto.getTitle();
		this.addr = dto.getAddr();
		this.categ = dto.getCateg();
		this.hashTag = dto.getHashTag();
		this.context = dto.getContext();
		this.publicYn = dto.getPublicYn();
	}
	
	public void changeMemberMyspot(MemberMyspotDto dto) {
		this.title = dto.getTitle();
		this.addr = dto.getAddr();
		this.categ = dto.getCateg();
		this.hashTag = dto.getHashTag();
		this.context = dto.getContext();
		this.publicYn = dto.getPublicYn();
	}

}
