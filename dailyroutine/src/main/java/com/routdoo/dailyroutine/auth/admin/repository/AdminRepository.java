package com.routdoo.dailyroutine.auth.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.auth.admin.domain.Admin;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.admin.repository
* @fileName      : AdminRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.16
* @description   : 관리자 jpa repositroy
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.16        ghgo       최초 생성
 */
public interface AdminRepository extends JpaRepository<Admin, String>,AdminCustomRepository{

	/**
	 * 아이디와 비밀번호 확인
	 * @param id
	 * @param pw
	 * @return
	 */
	Admin findByIdAndPw(String id, String pw);
	
}
