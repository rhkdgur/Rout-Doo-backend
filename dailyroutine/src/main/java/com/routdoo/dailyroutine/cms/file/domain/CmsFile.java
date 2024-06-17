package com.routdoo.dailyroutine.cms.file.domain;

import com.routdoo.dailyroutine.cms.file.dto.CmsFileDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.cms.file.domain
* @fileName      : CmsFile.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.18
* @description   : 파일처리 엔티티
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.18        ghgo       최초 생성
 */
@Entity
@Table(name="cms_file")
//@Inheritance(strategy = InheritanceType.JOINED)//상속용 엔티티
//@DiscriminatorColumn//상속사용시 선언해줘야하는것
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CmsFile {

	/**일련번호*/
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Comment("부모키값")
	private String parentIdx;
	
	@Comment("업로드 코드")
	private String uploadCode;
	
	@Comment("실제파일명칭")
	private String originalFileName;
	
	@Comment("등록파일명칭")
	private String saveFileName;
	
	@Comment("파일크기")
	private String fileSize;
	
	@Comment("확장명")
	private String extension;
	
	@Comment("파일태그명칭")
	private String tagName;
	
	@Comment("파일경로")
	private String saveFilePath;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;

	@Builder
	public CmsFile(CmsFileDto dto) {
        if(dto.getIdx() > 0) {
			this.idx = dto.getIdx();
		}
		this.parentIdx = dto.getParentIdx();
		this.uploadCode = dto.getUploadCode();
		this.originalFileName = dto.getOriginalFileName();
		this.saveFileName = dto.getSaveFileName();
		this.saveFilePath = dto.getSaveFilePath();
		this.fileSize = dto.getFileSize();
		this.extension = dto.getExtension();
		this.tagName = dto.getTagName();
	}
	
}
