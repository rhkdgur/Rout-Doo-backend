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
public interface MemberRepository extends JpaRepository<Member, String>,MemberCustomRepostiroy{

}
