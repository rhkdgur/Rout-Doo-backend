package com.routdoo.dailyroutine.cms.upload.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "파일경로 일련코드값은 필수입력입니다.")
    private String code = "";

    /**
     * 제목
     */
    @NotNull(message = "파일경로 제목은 필수입력입니다. ")
    private String title = "";

    /**
     * 짧은 경로
     */
    @NotNull(message = "파일경로 짧은경로는 필수입력입니다.")
    private String shortPath = "";

    /**
     * 전체 경로
     */
    @NotNull(message = "파일경로 전체경로는 필수입력입니다.")
    private String path = "";

    /**
     * 사용여부
     */
    private String useYn = "";

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
