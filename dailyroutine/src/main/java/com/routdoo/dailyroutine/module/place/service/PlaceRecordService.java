package com.routdoo.dailyroutine.module.place.service;

import com.routdoo.dailyroutine.module.place.domain.PlaceRecordRemove;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordDto;
import com.routdoo.dailyroutine.module.place.dto.PlaceRecordRemoveDto;
import com.routdoo.dailyroutine.module.place.repository.PlaceRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.routdoo.dailyroutine.module.place.service
 * fileName       : PlaceRecordService
 * author         : rhkdg
 * date           : 2023-12-14
 * description    : 장소 정보 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        rhkdg       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceRecordService {

    private final PlaceRecordRepository placeRecordRepository;

    /**
     * 상세조회
     * @param dto
     * @return
     * @throws Exception
     */
    public PlaceRecordDto selectPlaceRecord(PlaceRecordDto dto) throws Exception {
        return placeRecordRepository.selectPlaceRecord(dto);
    }

    /**
     * 등록
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean insertPlaceRecord(PlaceRecordDto dto) throws Exception  {
        return placeRecordRepository.insertPlaceRecord(dto);
    }

    /**
     * 수정
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updatePlaceRecord(PlaceRecordDto dto) throws Exception {
        return placeRecordRepository.updatePlaceRecord(dto);
    }

    /**
     * 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean deletePlaceRecord(PlaceRecordDto dto) throws Exception {
        return placeRecordRepository.deletePlaceRecord(dto);
    }


    /**
     * 놀거리 정보 삭제 요청
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean insertPlaceRecordRemove(PlaceRecordRemoveDto dto) throws Exception {
        return placeRecordRepository.insertPlaceRemove(dto);
    }

    /**
     * 놀거리 정보 요청 삭제에 대한 수정
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updatePlaceRecordRemove(PlaceRecordRemoveDto dto) throws Exception {
        return placeRecordRepository.updatePlaceRemove(dto);
    }

    /**
     * 놀거리 정보 삭제 요청에 대한 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean deletePlaceRecordRemove(PlaceRecordRemoveDto dto) throws Exception {
        return placeRecordRepository.deletePlaceRemove(dto);
    }

    /**
     * 놀거리 정보 삭제에 댇한 승인여부 처리
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updatePlaceRemoveApproveType(PlaceRecordRemoveDto dto) throws Exception {
        return placeRecordRepository.updatePlaceRemoveApproveType(dto);
    }

}
