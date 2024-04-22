package com.routdoo.dailyroutine.cms.upload.dto;

import com.routdoo.dailyroutine.cms.upload.domain.FileUpload;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.cms.upload.dto
 * fileName       : FileUploadDto
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
public class FileUploadDto {

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

    /**
     * 경로 존쟁유무
     */
    private boolean pathFlag = false;

    public FileUpload toEntity() {
        return FileUpload.builder().dto(this).build();
    }


    public FileUploadDto(FileUpload fileUpload) {
        this.code = fileUpload.getCode();
        this.title = fileUpload.getTitle();
        this.shortPath = fileUpload.getShortPath();
        this.path = fileUpload.getPath();
        this.useYn = fileUpload.getUseYn();
    }

    public FileUploadDto(String code, String title, String shortPath, String path, String useYn) {
        this.code = code;
        this.title = title;
        this.shortPath = shortPath;
        this.path = path;
        this.useYn = useYn;
    }

}
