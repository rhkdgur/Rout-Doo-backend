package com.routdoo.dailyroutine.module.place.repository;

import com.routdoo.dailyroutine.module.place.dto.PlaceRemoveDto;
import com.routdoo.dailyroutine.module.place.service.PlaceRemoveType;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.repository
 * fileName       : PlaceRemoveCustomRepository
 * author         : rhkdg
 * date           : 2023-12-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-15        rhkdg       최초 생성
 */
public interface PlaceRemoveCustomRepository {

    /**
     * 장소 삭제 요청 목록 조회
     * @param dto
     * @return
     * @throws Exception
     */
    List<PlaceRemoveDto> selectPlaceRemoveList(PlaceRemoveDto dto) throws Exception;

    /**
     * 장소 삭제 요청 상세
     * @param dto
     * @return
     * @throws Exception
     */
    PlaceRemoveDto selectPlaceRemove(PlaceRemoveDto dto) throws Exception ;

    /**
     * 삭제 요청 등록
     * @param dto
     * @return
     * @throws Exception
     */
    boolean insertPlaceRemove(PlaceRemoveDto dto) throws Exception;

    /**
     * 삭제 요청 수정
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updatePlaceRemove(PlaceRemoveDto dto) throws Exception;

    /**
     * 승인 여부
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updatePlaceRemoveApproveType(PlaceRemoveDto dto) throws Exception;

}
