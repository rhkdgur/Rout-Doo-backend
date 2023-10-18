package com.routdoo.dailyroutine.cms.file.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

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
	
}
