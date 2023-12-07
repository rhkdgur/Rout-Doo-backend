package com.routdoo.dailyroutine.module.place.service;

import com.routdoo.dailyroutine.common.PostResultCodeType;
import com.routdoo.dailyroutine.common.PostServiceResult;
import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.domain.PlaceComment;
import com.routdoo.dailyroutine.module.place.domain.PlaceLike;
import com.routdoo.dailyroutine.module.place.dto.*;
import com.routdoo.dailyroutine.module.place.repository.PlaceCommentRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceLikeRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
	 * 삭제
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void deletePlace(PlaceDto dto) throws Exception {
		placeRepository.deleteById(dto.getPlaceNum());
	}

	/**
	 * 위치 기반 장소 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<PlaceSummaryInfo> selectPlaceSelfLocationList(PlaceDefaultDto searchDto) throws Exception {
		List<PlaceSummaryInfo> places = placeRepository.selectPlaceSelfLocationList(searchDto.getMapx(),searchDto.getMapy(),searchDto.getPageable());
		
		return places;
	}

	/**
	 * 마이페이지 장소 보관 목록 페이징 o
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<Map<String,Object>> selectMypagePlaceLikePageList(PlaceLikeDefaultDto searchDto) throws  Exception {
		return placeRepository.selectMyPlaceLikePageList(searchDto);
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

	/**
	 * 댓글 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean insertPlaceComment(PlaceCommentDto dto) throws Exception {
		Long result = placeRepository.insertPlaceComment(dto);
		return result > 0;
	}

	/**
	 * 댓글 수정
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean updatePlaceComment(PlaceCommentDto dto) throws Exception {
		Long result = placeRepository.updatePlaceComment(dto);
		return result > 0;
	}

	/**
	 * 댓글 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean deletePlaceComment(PlaceCommentDto dto) throws Exception {
		Long result = placeRepository.deletePlaceComment(dto);
		return result > 0;
	}
			

	/**
	 * 답글 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<PlaceReplyCommentDto> selectPlaceReplyCommentList(PlaceDefaultDto searchDto) throws Exception {
		return placeRepository.selectPlaceReplyCommentList(searchDto);
	}

	/**
	 * 답글 단일 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PlaceReplyCommentDto selectPlaceReplyComment(PlaceReplyCommentDto dto) throws Exception {
		return placeRepository.selectPlaceReplyComment(dto);
	}

	/**
	 * 답글 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean insertPlaceReplyComment(PlaceReplyCommentDto dto) throws Exception {
		return placeRepository.insertPlaceReplyComment(dto) > 0;
	}

	/**
	 * 답글 수정
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean updatePlaceReplyComment(PlaceReplyCommentDto dto) throws Exception {
		return placeRepository.updatePlaceReplyComment(dto) > 0;
	}

	/**
	 * 답글 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean deletePlaceReplyComment(PlaceReplyCommentDto dto) throws Exception {
		return placeRepository.deletePlaceReplyComment(dto) > 0;
	}

	/**
	 * 좋아요 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PlaceLikeDto selectPlaceLike(PlaceLikeDto dto) throws Exception {
		return placeRepository.selectPlaceLike(dto);
	}

	/**
	 * 좋아요 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean insertPlaceLike(PlaceLikeDto dto) throws Exception {
		return placeRepository.insertPlaceLike(dto) > 0;
	}

	/**
	 * 좋아요 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean deletePlaceLike(PlaceLikeDto dto) throws Exception {
		return placeRepository.deletePlaceLike(dto) > 0;
	}

}
