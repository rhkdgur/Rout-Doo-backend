package com.routdoo.dailyroutine.auth.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.auth.jwt.domain.JwtTokenEntity;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt.repository
* @fileName      : JwtTokenEntityRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.30
* @description   : jwt 엔티티 테이블 사용
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.30        ghgo       최초 생성
 */
public interface JwtTokenEntityRepository extends JpaRepository<JwtTokenEntity, String>{

}
