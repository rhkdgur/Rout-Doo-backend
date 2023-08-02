package com.routdoo.dailyroutine.module.place.web;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routdoo.dailyroutine.common.web.BaseController;
import com.routdoo.dailyroutine.module.place.dto.PlaceDefaultDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.place.service.PlaceService;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.web
* @fileName      : PlaceController.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.01
* @description   : 관리자 장소 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.01        ghgo       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class PlaceController extends BaseController{

	private final PlaceService placeService;
	
	
	/**
	 * 장소 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(MGN_URL+"/place/list")
	public Map<String,Object> selectPlaceList(PlaceDefaultDto searchDto) throws Exception {
		
		Page<PlaceDto> places = placeService.selectPlacePageList(searchDto);
		modelMap.put("placeList", places);
		
		return modelMap;
	}
	
	/**
	 * 장소 상세 정보
	 * @param placeNum
	 * @return
	 * @throws Exception
	 */
	@GetMapping(MGN_URL+"/place/view")
	public Map<String,Object> selectPlaceView(@RequestParam("placeNum") String placeNum) throws Exception {
		
		PlaceDto dto = new PlaceDto();
		dto.setPlaceNum(placeNum);
		
		dto = placeService.selectPlaceView(dto);
		
		modelMap.put("place", dto);
		
		return modelMap;
	}
	
	/**
	 * 장소 등록
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/place/act/ins")
	public ResponseEntity<String> insertPlace(PlaceDto dto) throws Exception {
		
		try {
			placeService.savePlace(dto);
		}catch (Exception e) {
			return new ResponseEntity<String>("장소 등록시 오류가 발생하였습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("등록 되었습니다.",HttpStatus.OK);
				
	}
	
	/**
	 * 장소 수정
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/place/act/upd")
	public ResponseEntity<String> updatePlace(PlaceDto dto) throws Exception {
		
		try {
			placeService.savePlace(dto);
		}catch (Exception e) {
			return new ResponseEntity<String>("장소 수정시 오류가 발생하였습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("수정 되었습니다.",HttpStatus.OK);
	}
	
}
