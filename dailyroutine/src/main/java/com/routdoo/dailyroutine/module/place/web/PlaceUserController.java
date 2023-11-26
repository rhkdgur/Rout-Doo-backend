package com.routdoo.dailyroutine.module.place.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name="장소 사용자 컨트롤러")
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
	@Operation(summary="사용자 위치 주변 목록 조회")
	@Parameters( value = {
		@Parameter(name = "pstatus", description ="사용상태"),
		@Parameter(name = "placeNum", description ="장소번호"),
		@Parameter(name = "title", description ="제목(장소)"),
		@Parameter(name = "hashTag", description ="해쉬태그"),
		@Parameter(name = "addr", description ="주소"),
		@Parameter(name = "mapx", description ="경도"),
		@Parameter(name = "mapy", description ="위도")
	})
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
	@Operation(summary="장소 목록")
	@Parameters(value = {
		@Parameter(name = "pstatus", description="사용상태"),
		@Parameter(name = "placeNum", description="장소번호"),
		@Parameter(name = "title", description="제목(장소)"),
		@Parameter(name = "hashTag", description="해쉬태그"),
		@Parameter(name = "addr", description="주소"),
		@Parameter(name = "mapx", description="경도"),
		@Parameter(name = "mapy", description="위도")
	})
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
	@Operation(summary="장소 상세 조회")
	@Parameter(name = "placeNum", description="장소번호")
	@GetMapping(API_URL+"/place/view")
	public Map<String,Object> selectPlaceView(@RequestParam("placeNum") String placeNum) throws Exception {
		
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
}
