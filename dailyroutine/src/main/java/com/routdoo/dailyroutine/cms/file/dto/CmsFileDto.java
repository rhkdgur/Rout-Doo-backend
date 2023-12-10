package com.routdoo.dailyroutine.cms.file.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.routdoo.dailyroutine.cms.file.domain.CmsFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.cms.file.dto
* @fileName      : CmsFileDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.18
* @description   : 파일 관리 Dto
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.18        ghgo       최초 생성
 */
@Getter
@Setter
public class CmsFileDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**일련번호*/
	private Long idx;
	
	/**부모키값*/
	private String parentIdx;
	
	/**업로드코드*/
	private String uploadCode;
	
	/**실제파일명*/
	private String originalFileName;
	
	/**저장된파일명*/
	private String saveFileName;
	
	/**파일크기*/
	private String fileSize;
	
	/**확장명*/
	private String extension;
	
	/**파일태그명*/
	private String tagName;
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;

	/**
	 * 파일
	 */
	private MultipartFile multiPartFile;


	public CmsFileDto(CmsFile entity){
		this.idx = entity.getIdx();
		this.parentIdx = entity.getParentIdx();
		this.uploadCode = entity.getUploadCode();
		this.originalFileName = entity.getOriginalFileName();
		this.saveFileName = entity.getSaveFileName();
		this.fileSize = entity.getFileSize();
		this.extension = entity.getExtension();
		this.tagName = entity.getTagName();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}
	
}
