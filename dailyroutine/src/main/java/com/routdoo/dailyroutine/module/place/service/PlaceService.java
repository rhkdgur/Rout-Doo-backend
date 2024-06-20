package com.routdoo.dailyroutine.module.place.service;

import com.routdoo.dailyroutine.cms.file.service.CmsFileService;
import com.routdoo.dailyroutine.common.exception.handler.NoMatchDataException;
import com.routdoo.dailyroutine.module.place.domain.Place;
import com.routdoo.dailyroutine.module.place.domain.PlaceComment;
import com.routdoo.dailyroutine.module.place.domain.PlaceIntro;
import com.routdoo.dailyroutine.module.place.dto.*;
import com.routdoo.dailyroutine.module.place.repository.PlaceCommentRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceIntroRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceLikeRepository;
import com.routdoo.dailyroutine.module.place.repository.PlaceRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

	private final PlaceIntroRepository placeIntroRepository;
	
	private final PlaceCommentRepository placeCommentRepository;

	private final CmsFileService cmsFileService;
	
	/**
	 * 장소 목록(페이징 o)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<PlaceSummaryResponse> selectPlacePageList(PlaceDefaultDto searchDto) throws Exception {
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
	 * 등록
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public String insertPlace(PlaceDto dto) throws Exception {

		try {
			//장소번호 생성
			String placeNum = placeRepository.selectPlaceNumMax();
			dto.setPlaceNum(placeNum);
			Place place = new Place(dto);
			placeRepository.save(place);
			return placeNum;
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 장소 수정
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void updatePlace(PlaceDto dto) throws  Exception {
		try {
			//장소 대표 정보 존재 여부 확인
			if (!StringUtils.isBlank(dto.getPlaceNum())) {
				throw new NullPointerException("잘못된 접근입니다.");
			}

			Place place = placeRepository.findById(dto.getPlaceNum()).orElse(null);
			if (place == null) {
				throw new NullPointerException("장소 정보가 존재하지 않습니다.");
			}
			
			//장소 업데이트
			place.chagnePlace(dto);
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}
	}



	/**
	 * 삭제
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void deletePlace(PlaceDto dto) throws Exception {

		PlaceIntroDto introDto = new PlaceIntroDto();
		introDto.setPlaceNum(dto.getPlaceNum());
		List<PlaceIntroDto> introDtoList = placeRepository.selectPlaceIntroList(introDto);
		//먼저 존재하는 소개글들 부터 삭제
		for(PlaceIntroDto intro : introDtoList){
			placeRepository.deletePlaceIntro(intro);
		}
		placeRepository.deleteById(dto.getPlaceNum());
	}

	/**
	 * 상태 업데이트
	 * @param placeDto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean updatePlaceStatus(PlaceDto placeDto) throws Exception {
		return placeRepository.updatePlaceStatus(placeDto);
	}

	/**
	 * 장소 소개글 등록
	 * @param dto
	 * @throws Exception
	 */
	@Transactional
	public void insertPlaceIntro(PlaceIntroDto dto) throws Exception {
		try{
			PlaceIntro placeIntro = dto.toEntity();
			placeIntroRepository.save(placeIntro);
			cmsFileService.processFileCreate(dto);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Transactional
	public void updatePlaceIntro(PlaceIntroDto dto) throws Exception {
		PlaceIntro prev = placeIntroRepository.findById(dto.getIdx()).orElse(null);
		if(prev == null){
			throw new NoMatchDataException("잘못된 접근입니다.");
		}
		prev.addChangePlaceIntro(dto.toEntity());
		cmsFileService.processFileUpdate(dto);
	}

	/**
	 * 장소 소개글 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean deletePlaceIntro(PlaceIntroDto dto) throws Exception {
		cmsFileService.proccessFileDelete(dto);
		return placeRepository.deletePlaceIntro(dto);
	}


	/**
	 * 위치 기반 장소 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<PlaceSummaryInfo> selectPlaceSelfLocationList(PlaceDefaultDto searchDto) throws Exception {
		
		List<PlaceSummaryInfo> places = new ArrayList<>();
	
		//인기 정렬일 경우
		if(!searchDto.isPopulFlag()) {
			places = placeRepository.selectPlaceSelfLocationList(searchDto.getMapx(), searchDto.getMapy(), searchDto.getDistance());
		}else{
			places = placeRepository.selectPlaceSelfLocationPopulList(searchDto.getMapx(), searchDto.getMapy(), searchDto.getDistance());
		}
		
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
	 *
	 * @param placeintroDto
	 * @return
	 * @throws Exception
	 */
	public List<PlaceIntroDto> selectPlaceIntroList(PlaceIntroDto placeintroDto) throws Exception {
		return placeRepository.selectPlaceIntroList(placeintroDto);
	}
			

	/**
	 * 답글 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<PlaceReplyCommentResponse> selectPlaceReplyCommentList(PlaceDefaultDto searchDto) throws Exception {
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
