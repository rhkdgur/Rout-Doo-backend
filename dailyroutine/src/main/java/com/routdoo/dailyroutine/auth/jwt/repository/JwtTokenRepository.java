package com.routdoo.dailyroutine.auth.jwt.repository;

import org.springframework.data.repository.CrudRepository;

import com.routdoo.dailyroutine.auth.jwt.domain.JwtToken;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt.repository
* @fileName      : JwtTokenRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.20
* @description   : JWT 토큰 REDIS CRUD
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.20        ghgo       최초 생성
 */
public interface JwtTokenRepository extends CrudRepository<JwtToken, String>{

}
