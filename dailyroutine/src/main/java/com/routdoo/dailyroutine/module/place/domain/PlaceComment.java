package com.routdoo.dailyroutine.module.place.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.auth.member.domain.Member;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.domain
* @fileName      : PlaceComment.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.27
* @description   : 장소 댓글 정보
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.27        ghgo       최초 생성
 */
@Entity
@Table(name="place_comment")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PlaceComment {

	@Comment("일련번호")
	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long idx;
	
	@Comment("회원 일련번호")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ckey",nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;
	
	@Comment("장소 일련번호")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="place_num",nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Place place;
	
	@Comment("내용")
	@Lob
	private String context;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;
}
