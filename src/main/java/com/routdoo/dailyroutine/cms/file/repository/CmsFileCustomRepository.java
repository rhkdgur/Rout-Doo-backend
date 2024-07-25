package com.routdoo.dailyroutine.cms.file.repository;

import com.routdoo.dailyroutine.cms.file.dto.CmsFileDefaultDto;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileDto;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.file.repository
 * fileName       : CmsFileCustomRepository
 * author         : ghgo
 * date           : 2023/12/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        ghgo       최초 생성
 */
public interface CmsFileCustomRepository {

    /**
     * 파일 목록 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<CmsFileDto> selectCmsFileList(CmsFileDefaultDto searchDto) throws Exception;

    /**
     * 파일 상세 조회
     * @param dto
     * @return
     * @throws Exception
     */
    CmsFileDto selectCmsFile(CmsFileDto dto) throws Exception;

    /**
     * 파일 개수 조회
     * @param searchDto
     * @return
     * @throws Exception
     */
    Long selectCmsFileTotalCount(CmsFileDefaultDto searchDto) throws Exception;

    /**
     * 파일 등록
     * @param dto
     * @return
     * @throws Exception
     */
    boolean insertCmsFile(CmsFileDto dto) throws Exception;

    /**
     * 파일 수정
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updateCmsFile(CmsFileDto dto) throws Exception;

    /**
     * 파일 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    boolean deleteCmsFile(CmsFileDto dto) throws Exception;

}
