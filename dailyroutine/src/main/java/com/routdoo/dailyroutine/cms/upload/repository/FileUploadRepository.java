package com.routdoo.dailyroutine.cms.upload.repository;

import com.routdoo.dailyroutine.cms.upload.domain.FileUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.upload.repository
 * fileName       : FileUploadRepository
 * author         : rhkdg
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        rhkdg       최초 생성
 */
public interface FileUploadRepository extends JpaRepository<FileUpload,Long>,FileUploadCustomRepository {

    /**
     * 파일 조회 (사용유무 확인)
     * @param code
     * @param useYn
     * @return
     */
    FileUpload findByCodeAndUseYn(String code, String useYn);

    /**
     * 파일 조회
     * @param code
     * @return
     */
    FileUpload findByCode(String code);

    List<FileUpload> findByPathContainingIgnoreCaseOrShortPathContainingIgnoreCase(String path, String shortPath);

    Page<FileUpload> findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(String code, String title, Pageable pageable);

}
