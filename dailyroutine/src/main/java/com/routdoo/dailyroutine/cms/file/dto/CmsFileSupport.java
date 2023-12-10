package com.routdoo.dailyroutine.cms.file.dto;

/**
 * packageName    : com.routdoo.dailyroutine.cms.file.dto
 * fileName       : CmsFileSupport
 * author         : GAMJA
 * date           : 2023/12/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        GAMJA       최초 생성
 */
public interface CmsFileSupport <T>{

    /**
     * 파일 부모 정보
     * @return
     */
    String getParentIdx();

    /**
     * 파일 업로드 코드
     * @return
     */
    String getUploadCode();

    /**
     * 파일 목록
     * @return
     */
    T[] getCmsFileList();

    /**
     * 파일 set
     * @param t
     */
    void addCmsFileList(T t);

}
