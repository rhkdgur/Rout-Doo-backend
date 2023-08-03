package com.routdoo.dailyroutine.module.place.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceSummaryInfo;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.repository
* @fileName      : PlaceRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.27
* @description   : 장소 repository
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.27        ghgo       최초 생성
 */
public interface PlaceRepository extends JpaRepository<Place, String>,PlaceCustomRepository{
	
	@Query(value="SELECT"
			+ "	p.place_num placeNum, "
			+ "	p.title title,"
			+ " p.mapx mapx,"
			+ " p.mapy mapy,"
			+ " likeCnt,"
			+ " commentCnt,"
			+ "   ("
			+ "       6371 * acos ( cos ( radians(:#{#place.mapx}) ) "
			+ "          * cos( radians( p.mapx ) ) "
			+ "          * cos( radians( p.mapy ) - radians(:#{#place.mapy}) ) "
			+ "          + sin ( radians(:#{#place.mapx}) ) * sin( radians( p.mapx) ) "
			+ "       )"
			+ "   ) AS distance "
			+ "	FROM place p "
			+ " left join ( select count(*) likeCnt from place_like ) pl on pl.place_num = p.place_num "
			+ " left join ( select count(*) commentCnt from place_comment ) pc on pc.place_num = p.place_num "
			+ " WHERE p.pstatus = 'Y' "
			+ " HAVING distance < 2 "
			+ "	ORDER BY distance "
			+ "	LIMIT :#{#place.pageable.offset}, :#{#place.pageable.pageSize}"
    ,nativeQuery = true)
	List<PlaceSummaryInfo> selectPlaceSelfLocationList(@Param("place") PlaceDefaultDto searchDto) throws Exception;

}
