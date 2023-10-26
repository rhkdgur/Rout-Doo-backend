package com.routdoo.dailyroutine.module.place.web;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routdoo.dailyroutine.common.web.BaseController;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.PlaceCommentDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceSummaryInfo;
import com.routdoo.dailyroutine.module.place.service.PlaceService;

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
@RestController
@RequiredArgsConstructor
public class PlaceUserController extends BaseModuleController{

	private final PlaceService placeService;
	
	
	/**
	 * 사용자 위치 주변 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(API_URL+"/place/mylocation/list")
	public Map<String,Object> selectPlaceMyLocationList(PlaceDefaultDto searchDto) throws Exception {
		System.out.println("##### mapx :"+searchDto.getMapx());
		//목록 조회
		List<PlaceSummaryInfo> places = placeService.selectPlaceSelfLocationList(searchDto);
		modelMap.put("placeList", places);
		
		return modelMap;
	}
	
	/**
	 * 장소 목록
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(API_URL+"/place/list")
	public Map<String,Object> selectPlaceList(PlaceDefaultDto searchDto) throws Exception {
		
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
	@GetMapping(API_URL+"/place/view")
	public Map<String,Object> selectPlaceView(@RequestParam("placeNum") String placeNum) throws Exception {
		
		PlaceDto dto = new PlaceDto();
		dto.setPlaceNum(placeNum);
		
		dto = placeService.selectPlaceView(dto);
		//장소 상세 정보
		modelMap.put("place", dto);
		
		PlaceDefaultDto searchDto = new PlaceDefaultDto();
		searchDto.setPlaceNum(dto.getPlaceNum());
		Page<PlaceCommentDto>  comments = placeService.selectPlaceCommentPageList(searchDto);
		//댓글 목록
		modelMap.put("commentList", comments);
		
		return modelMap;
	}
}
