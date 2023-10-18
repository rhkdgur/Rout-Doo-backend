package com.routdoo.dailyroutine.auth.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.auth.member.domain.MemberMyspot;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member.repository
* @fileName      : MemberMyspotRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.18
* @description   : 나만의 장소 repository
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.18        ghgo       최초 생성
 */
public interface MemberMyspotRepository extends JpaRepository<MemberMyspot, Long>{

}
