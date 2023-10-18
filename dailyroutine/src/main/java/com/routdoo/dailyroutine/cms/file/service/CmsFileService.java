package com.routdoo.dailyroutine.cms.file.service;

import org.springframework.stereotype.Service;

import com.routdoo.dailyroutine.cms.file.repository.CmsFileRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.cms.file.service
* @fileName      : CmsFileService.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.18
* @description   : 파일 목록 처리 서비스
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.18        ghgo       최초 생성
 */
@Service
@RequiredArgsConstructor
public class CmsFileService {

	private final CmsFileRepository cmsFileRepository;
}
