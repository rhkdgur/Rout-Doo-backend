package com.routdoo.dailyroutine.cms.publiccode.repository;

import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDefaultDto;
import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.publiccode.repository
 * fileName       : PublicCodeRepository
 * author         : GAMJA
 * date           : 2023/12/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/19        GAMJA       최초 생성
 */
public interface PublicCodeRepository {

    /**
     * 공통 코드 목록(페이징)
     * @param searchDto
     * @return
     * @throws Exception
     */
    Page<PublicCodeDto> selectPublicCodePageList(PublicCodeDefaultDto searchDto) throws Exception;

    /**
     * 공통 코드 목록(페이징 x)
     * @param searchDto
     * @return
     * @throws Exception
     */
    List<PublicCodeDto> selectPublicCodeList(PublicCodeDefaultDto searchDto) throws Exception;

    /**
     * 공통 코드 개수
     * @param searchDto
     * @return
     * @throws Exception
     */
    Long selectPublicCodeTotalCount(PublicCodeDefaultDto searchDto) throws Exception;

    /**
     * 등록
     * @param searchDto
     * @return
     * @throws Exception
     */
    boolean insertPubliceCode(PublicCodeDto dto) throws Exception;

    /**
     * 수정
     * @param searchDto
     * @return
     * @throws Exception
     */
    boolean updatePublicCode(PublicCodeDto dto) throws Exception;

    /**
     * 삭제
     * @param searchDto
     * @return
     * @throws Exception
     */
    boolean deletePublicCode(PublicCodeDto dto) throws Exception;
}
