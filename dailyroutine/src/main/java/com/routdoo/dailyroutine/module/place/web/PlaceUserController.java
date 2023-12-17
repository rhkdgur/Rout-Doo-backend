package com.routdoo.dailyroutine.module.place.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.PlaceCommentDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceSummaryInfo;
import com.routdoo.dailyroutine.module.place.service.PlaceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.web
* @fileName      : PlaceUserController.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.03
* @description   : 장소 사용자 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.03        ghgo       최초 생성
 */
@Tag(name="장소 사용자 컨트롤러",description = "장소에 대한 데이터 처리를 합니다.")
@RestController
@RequiredArgsConstructor
public class PlaceUserController extends BaseModuleController{

	private final PlaceService placeService;

	private final MemberSession memberSession;
	
	
	/**
	 * 사용자 위치 주변 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="사용자 위치 주변 목록 조회 (놀거리 ,일정 지도 검색 찾기 사용, 페이징 x)")
	@Parameters( value = {
		@Parameter(name = "pstatus", description ="사용상태"),
		@Parameter(name = "placeNum", description ="장소번호"),
		@Parameter(name = "title", description ="제목(장소)"),
			@Parameter(name = "categCd" , description = "카테고리 코드"),
		@Parameter(name = "addr", description ="주소"),
		@Parameter(name = "mapx", description ="경도"),
		@Parameter(name = "mapy", description ="위도"),
			@Parameter(name = "distance", description = "주변 사정거리 ( ex: default 값은 2km 이내 입니다)"),
			@Parameter(name = "populFlag", description = "인기 정렬 일 경우 true, 아닐경우 false")
	})
	@GetMapping(API_URL+"/place/mylocation/list")
	public Map<String,Object> selectPlaceMyLocationList(PlaceDefaultDto searchDto) throws Exception {

		modelMap = new LinkedHashMap<>();
		//목록 조회
		List<PlaceSummaryInfo> places = placeService.selectPlaceSelfLocationList(searchDto);
		modelMap.put("placeList", places);
		
		return modelMap;
	}
	
	/**
	 * 장소 목록 (지도 검색)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(API_URL+"/place/list")
	@Operation(summary="일반 장소 목록(페이징 o)")
	@Parameters(value = {
		@Parameter(name = "pstatus", description="사용상태"),
		@Parameter(name = "placeNum", description="장소번호"),
		@Parameter(name = "title", description="제목(장소)"),
			@Parameter(name = "categCd" , description = "카테고리 코드"),
		@Parameter(name = "addr", description="주소"),
		@Parameter(name = "mapx", description="경도"),
		@Parameter(name = "mapy", description="위도"),
			@Parameter(name="cpage", description = "페이지 번호")
	})
	public Map<String,Object> selectPlaceList(PlaceDefaultDto searchDto) throws Exception {

		modelMap = new LinkedHashMap<>();
		
		Page<PlaceDto> places = placeService.selectPlacePageList(searchDto);
		modelMap.put("placeList", places);
		
		return modelMap;
	}
	
	
	/**
	 * 장소 상세 조회
	 * @param placeNum
	 * @return
	 * @throws Exception
	 */
	@Operation(summary="장소 상세 조회")
	@Parameter(name = "placeNum", description="장소번호")
	@GetMapping(API_URL+"/place/view")
	public Map<String,Object> selectPlaceView(@RequestParam("placeNum") String placeNum) throws Exception {

		modelMap = new LinkedHashMap<>();
		
		PlaceDto dto = new PlaceDto();
		dto.setPlaceNum(placeNum);
		
		dto = placeService.selectPlaceView(dto);
		//장소 상세 정보
		modelMap.put("place", dto);
		
		//댓글 목록
		PlaceDefaultDto searchDto = new PlaceDefaultDto();
		searchDto.setPlaceNum(dto.getPlaceNum());
		Page<PlaceCommentDto>  comments = placeService.selectPlaceCommentPageList(searchDto);

		String memberId = memberSession.isAuthenticated() ? memberSession.getMemberSession().getId() : "";

		Map<String,Object> commentsMap = new LinkedHashMap<>();
		List<Map<String,Object>> commentsList = new ArrayList<>();
		for(PlaceCommentDto commentDto : comments){
			commentsList.add(commentDto.toSummaryMap(memberId));
		}

		commentsMap.put("content",commentsList);
		commentsMap.put("totalPages",comments.getTotalPages());
		commentsMap.put("totalElements",comments.getTotalElements());
		commentsMap.put("pageable",comments.getPageable());

		//댓글 목록
		modelMap.put("commentList", commentsMap);
		
		return modelMap;
	}

	/**
	 * 장소 등록
	 * @param placeDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping(API_URL+"/place/act/ins")
	public ResponseEntity<String> insertPlace(PlaceDto placeDto) throws Exception {
		try{
			placeDto.setMemberId(memberSession.getMemberSession().getId());
			placeService.savePlace(placeDto);
		}catch (Exception e){
			logger.error("### insert place error : {}",e.getMessage());
			return new ResponseEntity<>("장소 등록시 오류가 발생하였습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<>("등록 되었습니다.",HttpStatus.OK);
	}

	/**
	 * 장소 수정
	 * @param placeDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping(API_URL+"/place/act/upd")
	public ResponseEntity<String> updatePlace(PlaceDto placeDto) throws Exception {
		try{
			placeDto.setMemberId(memberSession.getMemberSession().getId());
			placeService.savePlace(placeDto);
		}catch (Exception e){
			logger.error("### update place error : {}",e.getMessage());
			return new ResponseEntity<>("장소 수정시 오류가 발생하였습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<>("수정 되었습니다.",HttpStatus.OK);
	}

	/**
	 * 장소 삭제
	 * @param placeNum
	 * @return
	 * @throws Exception
	 */
	@PostMapping(API_URL+"/place/act/del")
	public ResponseEntity<String> deletePlace(@RequestParam("placeNum") String placeNum) throws Exception {

		try{
			PlaceDto placeDto = new PlaceDto();
			placeDto.setPlaceNum(placeNum);
			placeService.deletePlace(placeDto);
		}catch (Exception e){
			logger.error("### delete place error : {}",e.getMessage());
			return new ResponseEntity<>("장소 삭제시 오류가 발생했습니다.",HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
	}
}
