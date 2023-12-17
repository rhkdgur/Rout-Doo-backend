package com.routdoo.dailyroutine.module.place.repository;

import com.routdoo.dailyroutine.module.place.domain.PlaceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.repository
 * fileName       : PlaceRecordRepository
 * author         : rhkdg
 * date           : 2023-12-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        rhkdg       최초 생성
 */
public interface PlaceRecordRepository extends JpaRepository<PlaceRecord,Long> , PlaceRecordCustomRepository, PlaceRecordRemoveCustomRepository {
}
