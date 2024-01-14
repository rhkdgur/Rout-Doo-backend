package com.routdoo.dailyroutine.module.place.repository;

import com.routdoo.dailyroutine.module.place.dto.PlaceIntroDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordDto;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.repository
 * fileName       : PlaceRecordCustomRepository
 * author         : rhkdg
 * date           : 2023-12-14
 * description    : 장소 정보 수정 custom repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        rhkdg       최초 생성
 */
public interface PlaceRecordCustomRepository {

    /**
     * 목록 조회
     * @param placeRecordDto
     * @return
     * @throws Exception
     */
    List<PlaceRecordDto> selectPlaceRecordList(PlaceRecordDto placeRecordDto) throws  Exception;

    /**
     * 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    PlaceRecordDto selectPlaceRecord(PlaceRecordDto dto) throws Exception;

    /**
     * 등록
     * @param dto
     * @return
     * @throws Exception
     */
    boolean insertPlaceRecord(PlaceRecordDto dto) throws Exception;

    /**
     * 수정
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updatePlaceRecord(PlaceRecordDto dto) throws Exception;

    /**
     * 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    boolean deletePlaceRecord(PlaceRecordDto dto) throws Exception;

    /**
     * 상태값 변경
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updatePlaceRecordUseType(PlaceRecordDto dto) throws Exception;

}
