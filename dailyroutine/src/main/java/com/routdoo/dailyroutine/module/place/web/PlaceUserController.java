package com.routdoo.dailyroutine.module.place.web;

import com.routdoo.dailyroutine.auth.member.MemberSession;
import com.routdoo.dailyroutine.common.util.FileUploadUtil;
import com.routdoo.dailyroutine.common.vo.CommonResponse;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import com.routdoo.dailyroutine.module.place.dto.*;
import com.routdoo.dailyroutine.module.place.dto.action.PlaceCreateRequest;
import com.routdoo.dailyroutine.module.place.dto.action.PlaceDeleteRequest;
import com.routdoo.dailyroutine.module.place.dto.action.PlaceUpdateRequest;
import com.routdoo.dailyroutine.module.place.dto.action.intro.PlaceIntroCreateRequest;
import com.routdoo.dailyroutine.module.place.dto.action.intro.PlaceIntroDeleteRequest;
import com.routdoo.dailyroutine.module.place.dto.action.intro.PlaceIntroUpdateRequest;
import com.routdoo.dailyroutine.module.place.dto.with.PlaceViewWIthCommentListResponse;
import com.routdoo.dailyroutine.module.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Gwang hyeok Go
 * @packageName : com.routdoo.dailyroutine.module.place.web
 * @fileName : PlaceUserController.java
 * @date : 2023.08.03
 * @description : 장소 사용자 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023.08.03        ghgo       최초 생성
 */
@Tag(name = "장소 사용자 컨트롤러", description = "장소에 대한 데이터 처리를 합니다.")
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class PlaceUserController extends BaseModuleController {

    private final PlaceService placeService;

    private final MemberSession memberSession;

	private final FileUploadUtil fileUploadUtil;


    /**
     * 사용자 위치 주변 목록 조회
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "사용자 위치 주변 목록 조회 (놀거리 ,일정 지도 검색 찾기 사용, 페이징 x)")
    @Parameters(value = {
            @Parameter(name = "pstatus", description = "사용상태", required = false),
            @Parameter(name = "placeNum", description = "장소번호", required = false),
            @Parameter(name = "title", description = "제목(장소)", required = false),
            @Parameter(name = "categCd", description = "카테고리 코드", required = false),
            @Parameter(name = "addr", description = "주소", required = false),
            @Parameter(name = "mapx", description = "경도", required = false),
            @Parameter(name = "mapy", description = "위도", required = false),
            @Parameter(name = "distance", description = "주변 사정거리 ( ex: default 값은 2km 이내 입니다)", required = false),
            @Parameter(name = "populFlag", description = "인기 정렬 일 경우 true, 아닐경우 false", required = false)
    })
    @GetMapping(API_URL + "/place/mylocation/list")
    public List<PlaceSummaryInfo> selectPlaceMyLocationList(@Parameter(hidden = true) PlaceDefaultDto searchDto) throws Exception {
        return placeService.selectPlaceSelfLocationList(searchDto);
    }

    /**
     * 장소 목록 (지도 검색)
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "일반 장소 목록(페이징 o)")
    @Parameters(value = {
            @Parameter(name = "pstatus", description = "사용상태", required = false),
            @Parameter(name = "placeNum", description = "장소번호", required = false),
            @Parameter(name = "title", description = "제목(장소)", required = false),
            @Parameter(name = "categCd", description = "카테고리 코드", required = false),
            @Parameter(name = "addr", description = "주소", required = false),
            @Parameter(name = "mapx", description = "경도", required = false),
            @Parameter(name = "mapy", description = "위도", required = false),
            @Parameter(name = "page", description = "페이지 번호", required = false)
    })
    @GetMapping(API_URL + "/place/list")
    public Page<PlaceSummaryResponse> selectPlaceList(@Parameter(hidden = true) PlaceDefaultDto searchDto) throws Exception {
        return placeService.selectPlacePageList(searchDto);
    }

    /**
     * 장소 추가시 사용하는 검색 창
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 추가시 사용하는 검색")
    @Parameters(value = {
            @Parameter(name="sstring", description = "검색어",required = false),
            @Parameter(name = "page", description = "페이지 번호", required = false)
    })
    @GetMapping(API_URL+"/place/search")
    public Page<PlaceSummaryResponse> selectPlaceSearchList(@Parameter(hidden = true) PlaceDefaultDto searchDto) throws Exception {
        searchDto.setStype("search");
        return placeService.selectPlaceSearchList(searchDto);
    }


    /**
     * 장소 상세 조회
     *
     * @param placeNum
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 상세 조회")
    @Parameter(name = "placeNum", description = "장소번호")
    @GetMapping(API_URL + "/place/view")
    public PlaceViewWIthCommentListResponse selectPlaceView(@RequestParam("placeNum") String placeNum) throws Exception {

        modelMap = new LinkedHashMap<>();

        PlaceDto dto = new PlaceDto();
        dto.setPlaceNum(placeNum);
        //장소 상세 정보
        dto = placeService.selectPlaceView(dto);

        //댓글 목록
        PlaceDefaultDto searchDto = new PlaceDefaultDto();
        searchDto.setPlaceNum(dto.getPlaceNum());
        Page<PlaceCommentDto> comments = placeService.selectPlaceCommentPageList(searchDto);

        String memberId = memberSession.isAuthenticated() ? memberSession.getMemberSession().getId() : "";
        List<Map<String, Object>> commentsList = new ArrayList<>();
        for (PlaceCommentDto commentDto : comments) {
            commentsList.add(commentDto.toSummaryMap(memberId));
        }

        return PlaceViewWIthCommentListResponse.of(dto, commentsList);
    }

    /**
     * 장소 등록
     *
     * @param placeCreateRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "422", description = "등록 오류")
    })
    @PostMapping(value = API_URL + "/place/ins")
    public ResponseEntity<?> insertPlace(final @Valid @RequestBody PlaceCreateRequest placeCreateRequest) throws Exception {
        String placeNum = "";
        try {
            PlaceDto dto = PlaceDto.createOf(placeCreateRequest);
            dto.setMemberId(memberSession.getMemberSession().getId());
            dto.setPstatus("N");
            placeNum = placeService.insertPlace(dto);
        } catch (Exception e) {
            logger.error("### insert place error : {}", e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("장소 등록시 오류가 발생하였습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(CommonResponse.resAllOf("등록 되었습니다.",placeNum), HttpStatus.OK);
    }

    /**
     * 장소 수정
     *
     * @param placeUpdateRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "422", description = "수정 오류")
    })
    @PutMapping(value = API_URL + "/place/upd" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePlace(final @Valid @RequestPart PlaceUpdateRequest placeUpdateRequest) throws Exception {
        try {
            PlaceDto placeDto = PlaceDto.updateOf(placeUpdateRequest);
            placeDto.setMemberId(memberSession.getMemberSession().getId());
            placeService.updatePlace(placeDto);
        } catch (Exception e) {
            logger.error("### update place error : {}", e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("장소 수정시 오류가 발생하였습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("수정 되었습니다."), HttpStatus.OK);
    }

    /**
     * 장소 삭제
     *
     * @param placeDeleteRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 완료"),
            @ApiResponse(responseCode = "422", description = "삭제 오류")
    })
    @DeleteMapping(API_URL + "/place/del")
    public ResponseEntity<?> deletePlace(final @Valid @RequestBody PlaceDeleteRequest placeDeleteRequest) throws Exception {

        try {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setPlaceNum(placeDeleteRequest.getPlaceNum());
            placeService.deletePlace(placeDto);
        } catch (Exception e) {
            logger.error("### delete place error : {}", e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("장소 삭제시 오류가 발생했습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("삭제 되었습니다."), HttpStatus.OK);
    }


    /**
     * 장소 소개글 등록
     * @param placeIntroCreateRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 소개글 등록")
    @Parameters({
            @Parameter(name = "_file", description = "formData 에서 파일 형식, 파일 이름에 해당 , 파일명은 아무거나 선언가능 _file은 예시"),
            @Parameter(name = "_alt_file", description = " formData에서 String으로 전달,  파일의 비고명 아무거나 선언가능 대신 파일명의 name을 뒤에 그대로 붙여줘야함 _alt{파일name}")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "422", description = "등록 오류")
    })
    @PostMapping(value=API_URL+"/place/intro/ins", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertPlaceIntro(final @Valid @RequestPart PlaceIntroCreateRequest placeIntroCreateRequest) throws Exception {
        try{
            PlaceIntroDto dto = PlaceIntroDto.createOf(placeIntroCreateRequest);
            dto.setMemberId(memberSession.getMemberSession().getId());
            placeService.insertPlaceIntro(dto);
        }catch (Exception e){
            logger.error("### insert place intro error : {}", e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("장소 소개글 등록시 오류가 발생했습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok(CommonResponse.resOnlyMessageOf("등록 되었습니다."));
    }

    /**
     * 장소 소개글 수정
     * @param placeIntroUpdateRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 소개글 수정")
    @Parameters({
            @Parameter(name = "_file", description = "formData 에서 파일 형식 ,파일 이름에 해당 , 파일명은 아무거나 선언가능 _file은 예시"),
            @Parameter(name = "_alt_file", description = "formData 에서 string, 파일의 비고명 아무거나 선언가능 대신 파일명의 name을 뒤에 그대로 붙여줘야함 _alt{파일name}"),
            @Parameter(name = "_modify_file", description = "수정하는 파일에 대한 parent_idx 값, 파일의 비고명 아무거나 선언가능 대신 파일명의 name을 뒤에 그대로 붙여줘야함 _modify{파일name}"),
            @Parameter(name = "_delete_file", description = "삭제하는 파일에 대한 parent_idx 값, 파일의 비고명 아무거나 선언가능 대신 파일명의 name을 뒤에 그대로 붙여줘야함 _delete{파일name}")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "422", description = "등록 오류")
    })
    @PutMapping(value=API_URL+"/place/intro/upd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePlaceIntro(final @Valid @RequestPart PlaceIntroUpdateRequest placeIntroUpdateRequest) throws Exception {
        try{
            PlaceIntroDto dto = PlaceIntroDto.updateOf(placeIntroUpdateRequest);
            dto.setMemberId(memberSession.getMemberSession().getId());
            placeService.updatePlaceIntro(dto);
        }catch (Exception e){
            logger.error("### update place intro error : {}", e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("장소 소개글 수정시 오류가 발생했습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok(CommonResponse.resOnlyMessageOf("수정 되었습니다."));
    }

    /**
     * 장소 소개글 삭제
     * @param placeIntroDeleteRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "장소 소개글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 완료"),
            @ApiResponse(responseCode = "422", description = "등록 오류")
    })
    @DeleteMapping(value=API_URL+"/place/intro/del")
    public ResponseEntity<?> deletePlaceIntro(final @Valid @RequestBody PlaceIntroDeleteRequest placeIntroDeleteRequest) throws Exception {
        try{
            PlaceIntroDto dto = new PlaceIntroDto();
            dto.setIdx(placeIntroDeleteRequest.getIdx());
            dto.setMemberId(memberSession.getMemberSession().getId());
            placeService.deletePlaceIntro(dto);
        }catch (Exception e){
            logger.error("### delete place intro error : {}", e.getMessage());
            return new ResponseEntity<>(CommonResponse.resOnlyMessageOf("장소 소개글 삭제시 오류가 발생했습니다."), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok(CommonResponse.resOnlyMessageOf("삭제 되었습니다."));
    }
}
