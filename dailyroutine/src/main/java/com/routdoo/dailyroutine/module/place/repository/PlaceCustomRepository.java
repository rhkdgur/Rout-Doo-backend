package com.routdoo.dailyroutine.module.place.repository;

import com.routdoo.dailyroutine.module.place.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

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


	/**
	 * 좋아요 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	PlaceLikeDto selectPlaceLike(PlaceLikeDto dto) throws Exception;


	/**
	 * 좋아요 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long insertPlaceLike(PlaceLikeDto dto) throws Exception;


	/**
	 * 좋아요 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long deletePlaceLike(PlaceLikeDto dto) throws Exception;
	
	
	/***
	 * 장소 댓글 목록 페이징
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	Page<PlaceCommentDto> selectPlaceCommentPageList(PlaceDefaultDto searchDto) throws Exception;

	/**
	 * 댓글 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long insertPlaceComment(PlaceCommentDto dto) throws Exception;

	/**
	 * 댓글 수정
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long updatePlaceComment(PlaceCommentDto dto) throws Exception;

	/**
	 * 댓글 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long deletePlaceComment(PlaceCommentDto dto) throws Exception;
	

	/**
	 * 댓글에 대한 답글 목록
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	List<PlaceReplyCommentDto> selectPlaceReplyCommentList(PlaceDefaultDto searchDto) throws Exception;

	/**
	 * 댓글에 대한 답글 상세 정보
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	PlaceReplyCommentDto selectPlaceReplyComment(PlaceReplyCommentDto dto) throws Exception;

	/**
	 * 답글 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long insertPlaceReplyComment(PlaceReplyCommentDto dto) throws Exception;

	/**
	 * 답글 수정
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long updatePlaceReplyComment(PlaceReplyCommentDto dto) throws Exception;

	/**
	 * 답글 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Long deletePlaceReplyComment(PlaceReplyCommentDto dto) throws Exception;
}
