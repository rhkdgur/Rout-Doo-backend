package com.routdoo.dailyroutine.auth.user.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.user.domain
* @fileName      : Member.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.06
* @description   : 회원 정보 entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.06        ghgo       최초 생성
 */
@Entity
@Table(name="member")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

	@Id
	@Column(length = 30)
	@Comment("아이디")
	private String id;
	
	@Column(length = 100)
	@Comment("비밀번호")
	private String pw;
	
	@Column(length = 50)
	@Comment("이메일")
	private String email;
	
	@Column(length = 50)
	@Comment("닉네임")
	private String nickname;
	
	@Column(length = 30)
	@Comment("전화번호")
	private String phonenumber;
	
	@Column(length = 1, columnDefinition = "char")
	@Comment("성별")
	private String gender;
	
	@Comment("나이")
	private int age;
	
	@Column(length=20)
	@Comment("생년월일")
	private String birth;
	
	@Column(length=4)
	@Comment("MBTI")
	private String mbti;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;
	
}
