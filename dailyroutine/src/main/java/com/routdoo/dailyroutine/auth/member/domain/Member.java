package com.routdoo.dailyroutine.auth.member.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
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
	
	/**
	 * entity에 dto 정보 처리 생성자
	 * @param dto
	 */
	@Builder
	public Member(MemberDto dto) {
		this.id = dto.getId();
		this.pw = dto.getPw();
		this.email = dto.getEmail();
		this.nickname = dto.getNickname();
		this.phonenumber = dto.getPhonenumber();
		this.gender = dto.getGender();
		this.age = dto.getAge();
		this.birth = dto.getBirth();
		this.mbti = dto.getMbti();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}

	/**
	 * 전체 정보 업데이트 처리 메소드
	 * @param dto
	 */
	public void changeMember(MemberDto dto) {
		this.id = dto.getId();
		this.pw = dto.getPw();
		this.email = dto.getEmail();
		this.nickname = dto.getNickname();
		this.phonenumber = dto.getPhonenumber();
		this.gender = dto.getGender();
		this.age = dto.getAge();
		this.birth = dto.getBirth();
		this.mbti = dto.getMbti();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
}
