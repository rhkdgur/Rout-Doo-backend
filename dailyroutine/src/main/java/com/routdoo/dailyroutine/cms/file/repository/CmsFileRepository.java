package com.routdoo.dailyroutine.cms.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.cms.file.domain.CmsFile;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.cms.file.repository
* @fileName      : CmsFileRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.18
* @description   : cmsFile 리포지토리
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.18        ghgo       최초 생성
 */
public interface CmsFileRepository extends JpaRepository<CmsFile, Long>{

}
