package com.routdoo.dailyroutine.module.place.dto.with;

import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.dto
 * fileName       : PlaceViewWIthCommentListResponse
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 일정 상세 정보 및 댓글 목록 response
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Schema(description = "일정 상세 정보 및 댓글 목록 response")
@Getter
@Setter
@NoArgsConstructor
public class PlaceViewWIthCommentListResponse {

    private PlaceDto placeDto;

    private List<Map<String,Object>> commentList;

    /**
     * 일정 상세 정보 및 댓글 목록 method
     * @param placeDto
     * @param commentList
     * @return
     */
    public static PlaceViewWIthCommentListResponse of(PlaceDto placeDto, List<Map<String,Object>> commentList){
        PlaceViewWIthCommentListResponse response = new PlaceViewWIthCommentListResponse();
        response.setPlaceDto(placeDto);
        response.setCommentList(commentList);
        return response;
    }

}
