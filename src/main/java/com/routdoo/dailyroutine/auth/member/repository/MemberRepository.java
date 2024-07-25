package com.routdoo.dailyroutine.auth.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.auth.member.domain.Member;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.user.repository
* @fileName      : MemberRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.11
* @description   : 회원 repository
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.11        ghgo       최초 생성
 */
public interface MemberRepository extends JpaRepository<Member, String>,MemberCustomRepository{

	/***
	 * 아이디와 비밀번호 입력 확인
	 * @param id
	 * @param pw
	 * @return
	 */
	Member findByIdAndPw(String id, String pw);
}
