package com.routdoo.dailyroutine.module.place.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.routdoo.dailyroutine.module.place.domain.Place;
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
			+ " (select count(*) from place_like where place_num = p.place_num) likeCnt,"
			+ " (select count(*) from place_comment where place_num = p.place_num) commentCnt,"
			+ "   ("
			+ "       6371 * acos ( cos ( radians(:mapx) ) "
			+ "          * cos( radians( p.mapx ) ) "
			+ "          * cos( radians( p.mapy ) - radians(:mapy) ) "
			+ "          + sin ( radians(:mapx) ) * sin( radians( p.mapx) ) "
			+ "       )"
			+ "   ) AS distance "
			+ "	FROM place p "
			+ " WHERE p.pstatus = 'Y' "
			+ " HAVING distance < :distance "
			+ "	ORDER BY distance ",
			countQuery = ""
					+ "select count(*) from ("
					+ "select "
					+ "   ("
					+ "       6371 * acos ( cos ( radians(:mapx) ) "
					+ "          * cos( radians( p.mapx ) ) "
					+ "          * cos( radians( p.mapy ) - radians(:mapy) ) "
					+ "          + sin ( radians(:mapx) ) * sin( radians( p.mapx) ) "
					+ "       )"
					+ "   ) AS distance "
					+ " from place p"
					+ " WHERE p.pstatus = 'Y' "
					+ " HAVING distance < :distance "
					+ "	) T",
			nativeQuery = true)
	List<PlaceSummaryInfo> selectPlaceSelfLocationList(@Param("mapx") String mapx, @Param("mapy") String mapy , @Param("distance") int distance) throws Exception;
	
	
	
	/**
	 * 장소번호 별 조회
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Query("SELECT p from Place p where p.placeNum IN (:placeNum)")
	List<Place> selectPlaceNumListIn(@Param("placeNum") List<String> list) throws Exception;

}
