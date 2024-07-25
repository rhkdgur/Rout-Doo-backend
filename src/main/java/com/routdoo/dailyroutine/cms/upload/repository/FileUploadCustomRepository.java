package com.routdoo.dailyroutine.cms.upload.repository;

import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDefaultDto;
import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDto;
import org.springframework.data.domain.Page;

/**
 * packageName    : com.routdoo.dailyroutine.cms.upload.repository
 * fileName       : FileUploadCustomRepository
 * author         : rhkdg
 * date           : 2024-04-22
 * description    : 파일 업로드 경로 custom interface
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        rhkdg       최초 생성
 */
public interface FileUploadCustomRepository {

    /**
     * 파일 경로 목록 조회
     * @param searchDto
     * @return
     */
    Page<FileUploadDto> selectFileUploadPageList(FileUploadDefaultDto searchDto);
    
}
