package com.routdoo.dailyroutine.cms.upload.domain;

import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.cms.upload.domain
 * fileName       : FileUpload
 * author         : rhkdg
 * date           : 2024-04-22
 * description    : 파일 업로드 경로 설정 entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        rhkdg       최초 생성
 */
@Entity
@Table(name="cms_file_upload")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FileUpload implements Persistable<String> {

    /**파일경로 코드*/
    @Id
    @Column(length = 80)
    private String code;

    /**제목*/
    @Column(length = 100)
    private String title;

    /**짧은 경로*/
    @Column(length = 100)
    private String shortPath;

    /**전체 경로*/
    @Column(length = 100)
    private String path;

    /**사용여부*/
    @Column(columnDefinition = "char",length = 1)
    private String useYn;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;


    @Builder
    public FileUpload(FileUploadDto dto) {
        this.code = dto.getCode();
        this.title = dto.getTitle();
        this.shortPath = dto.getShortPath();
        this.path = dto.getPath();
        this.useYn = dto.getUseYn();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    public void addFileUpload(FileUploadDto dto) {
        this.code = dto.getCode();
        this.title = dto.getTitle();
        this.shortPath = dto.getShortPath();
        this.path = dto.getPath();
        this.useYn = dto.getUseYn();
    }

    @Override
    public String getId() {
        return this.code;
    }

    @Override
    public boolean isNew() {
        return this.createDate == null;
    }
}
