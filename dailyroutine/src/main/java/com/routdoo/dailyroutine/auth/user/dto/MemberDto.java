package com.routdoo.dailyroutine.auth.user.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.user.dto
* @fileName      : Member.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.11
* @description   : 회원 정보 DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.11        ghgo       최초 생성
 */
@Getter
@NoArgsConstructor
public class MemberDto {

	private String id;
	
	private String pw;
	
	private String email;
	
	private String nickname;
	
	private String phonenumber;
	
	private String gender;
	
	private int age;
	
	private String birth;
	
	private String mbti;
	
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	
}
