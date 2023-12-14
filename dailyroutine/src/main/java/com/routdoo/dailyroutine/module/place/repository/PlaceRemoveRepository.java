package com.routdoo.dailyroutine.module.place.repository;

import com.routdoo.dailyroutine.module.place.domain.PlaceRemove;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.repository
 * fileName       : PlaceRemoveRepository
 * author         : rhkdg
 * date           : 2023-12-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-15        rhkdg       최초 생성
 */
public interface PlaceRemoveRepository extends JpaRepository<PlaceRemove, Long>,PlaceRemoveCustomRepository {
}
