package com.routdoo.dailyroutine.module.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routdoo.dailyroutine.common.PostResultCodeType;
import com.routdoo.dailyroutine.common.PostServiceResult;
import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.domain.PlaceComment;
import com.routdoo.dailyroutine.module.place.domain.PlaceLike;
import com.routdoo.dailyroutine.module.place.dto.PlaceCommentDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceLikeDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceSummaryInfo;
import com.routdoo.dailyroutine.module.place.repository.PlaceCommentRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceLikeRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.service
* @fileName      : PlaceService.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.27
* @description   : 장소 리스트
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.27        ghgo       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

	private final PlaceRepository placeRepository;
	
	private final PlaceLikeRepository placeLikeRepository;
	
	private final PlaceCommentRepository placeCommentRepository; 
	
	/**
	 * 장소 목록(페이징 o)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<PlaceDto> selectPlacePageList(PlaceDefaultDto searchDto) throws Exception {
		return placeRepository.selectPlacePageList(searchDto);
	}
	
	/**
	 * 장소 목록(페이징 x)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<PlaceDto> selectPlaceList(PlaceDefaultDto searchDto) throws Exception {
		return placeRepository.selectPlaceList(searchDto);
	}
	
	/**
	 * 장소 상세
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PlaceDto selectPlaceView(PlaceDto dto) throws Exception {
		return placeRepository.selectPlaceView(dto);
	}
	
	/**
	 * 등록,수정
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void savePlace(PlaceDto dto) throws Exception {
		Place place = placeRepository.findById(dto.getPlaceNum()).orElse(null);
		//등록
		if(place == null) {
			placeRepository.save(dto.toEntity());
		//수정
		}else {
			place.chagnePlace(dto);
		}
	}
	
	/**
	 * 위치 기반 장소 조회
	 * @param mapx
	 * @param mapy
	 * @return
	 * @throws Exception
	 */
	public List<PlaceSummaryInfo> selectPlaceSelfLocationList(PlaceDefaultDto searchDto) throws Exception {
		List<PlaceSummaryInfo> places = placeRepository.selectPlaceSelfLocationList(searchDto.getMapx(),searchDto.getMapy(),searchDto.getPageable());
		
		return places;
	}
	
	/**
	 * 좋아요 등록
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public PostServiceResult<?> savePlaceLike(PlaceLikeDto dto) throws Exception {
		PlaceLike placeLike = placeLikeRepository.save(dto.toEntity());
		if(placeLike == null) {
			return new PostServiceResult<>(PostResultCodeType.FAIL);
		}
		return new PostServiceResult<>(PostResultCodeType.OK);
	}
	
	/**
	 * 좋아요 삭제
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public PostServiceResult<?> deletePlaceList(PlaceLikeDto dto) throws Exception {
		placeLikeRepository.deleteById(dto.getIdx());
		PlaceLike placeLike = placeLikeRepository.findById(dto.getIdx()).orElse(null);
		if(placeLike != null) {
			return new PostServiceResult<>(PostResultCodeType.FAIL);
		}
		return new PostServiceResult<>(PostResultCodeType.OK);
	}
	
	/**
	 * 댓글 목록
	 * @return
	 * @throws Exception
	 */
	public Page<PlaceCommentDto> selectPlaceCommentPageList(PlaceDefaultDto searchDto) throws Exception {
		return placeRepository.selectPlaceCommentPageList(searchDto);
	}
	
	/**
	 * 댓글 상세 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PlaceCommentDto selectPlaceCommentView(PlaceCommentDto dto) throws Exception {
		
		PlaceComment placeComment = placeCommentRepository.findById(dto.getIdx()).orElse(null);
		if(placeComment == null) {
			return null;
		}
		
		return new PlaceCommentDto(placeComment);
	}
}
