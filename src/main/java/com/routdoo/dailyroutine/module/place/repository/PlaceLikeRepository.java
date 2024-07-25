package com.routdoo.dailyroutine.module.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.module.place.domain.PlaceLike;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.repository
* @fileName      : PlaceLikeRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.27
* @description   : place like 리포지토리 인터페이스
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.27        ghgo       최초 생성
 */
public interface PlaceLikeRepository extends JpaRepository<PlaceLike, Long>{

}
