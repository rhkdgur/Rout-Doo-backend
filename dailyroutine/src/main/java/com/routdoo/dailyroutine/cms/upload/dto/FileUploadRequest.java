package com.routdoo.dailyroutine.cms.upload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.cms.upload.dto
 * fileName       : FileUploadRequest
 * author         : rhkdg
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class FileUploadRequest {
    /**
     * 파일경로 코드
     */
    private String code = "";

    /**
     * 제목
     */
    private String title = "";

    /**
     * 짧은 경로
     */
    private String shortPath = "";

    /**
     * 전체 경로
     */
    private String path = "";

    /**
     * 사용여부
     */
    private String useYn = "";

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
