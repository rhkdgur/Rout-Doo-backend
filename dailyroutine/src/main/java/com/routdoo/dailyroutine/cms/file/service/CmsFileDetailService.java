package com.routdoo.dailyroutine.cms.file.service;

import com.routdoo.dailyroutine.cms.file.dto.CmsFileSupport;
import com.routdoo.dailyroutine.cms.file.exception.ClassTypeMissMatchException;

/**
 * packageName    : com.routdoo.dailyroutine.cms.file.service
 * fileName       : CmsFileDetailService
 * author         : GAMJA
 * date           : 2023/12/10
 * description    : 파일 서버 저장 처리 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        GAMJA       최초 생성
 */
public interface CmsFileDetailService {

    /**
     * 파일 등록
     *
     * @param obj
     * @return
     * @throws Exception
     */
    <T extends CmsFileSupport<?>> boolean processFileCreate(T obj) throws Exception;

    /**
     * 파일 수정
     * @param obj
     * @return
     * @throws Exception
     */
    <T extends CmsFileSupport<?>>  boolean processFileUpdate(T obj) throws Exception;

    /**
     * 파일 삭제
     * @param obj
     * @return
     * @throws Exception
     */
    <T extends CmsFileSupport<?>> boolean proccessFileDelete(T obj) throws Exception;
}
