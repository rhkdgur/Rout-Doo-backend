package com.routdoo.dailyroutine.module.place.repository;

import com.routdoo.dailyroutine.module.place.domain.PlaceIntro;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.repository
 * fileName       : PlaceIntroRepository
 * author         : rhkdg
 * date           : 2024-06-20
 * description    : 장소 소개글 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-20        rhkdg       최초 생성
 */
public interface PlaceIntroRepository extends JpaRepository<PlaceIntro,Long> {
}
