package com.routdoo.dailyroutine.module.place.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.routdoo.dailyroutine.module.place.dto.PlaceCommentDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDto;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.repository
* @fileName      : PlaceCustomRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.27
* @description   : 장소 repository custom interface
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.27        ghgo       최초 생성
 */
public interface PlaceCustomRepository {

	/**
	 * 장소 목록(페이징 o)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<PlaceDto> selectPlacePageList(PlaceDefaultDto searchDto) throws Exception;
	
	/**
	 * 장소 목록(페이징 x)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	List<PlaceDto> selectPlaceList(PlaceDefaultDto searchDto) throws Exception;
	
	/**
	 * 장소 상세정보
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	PlaceDto selectPlaceView(PlaceDto dto) throws Exception;
	
	
	/***
	 * 장소 댓글 목록 페이징
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<PlaceCommentDto> selectPlaceCommentPageList(PlaceDefaultDto searchDto) throws Exception;
	

}
