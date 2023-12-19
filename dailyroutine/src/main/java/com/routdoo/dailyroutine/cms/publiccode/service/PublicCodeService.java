package com.routdoo.dailyroutine.cms.publiccode.service;

import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDefaultDto;
import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDto;
import com.routdoo.dailyroutine.cms.publiccode.repository.PublicCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.cms.publiccode.service
 * fileName       : PublicCodeService
 * author         : GAMJA
 * date           : 2023/12/19
 * description    : 공통코드 Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/19        GAMJA       최초 생성
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@CacheConfig(cacheNames = "public_code")
public class PublicCodeService {

    private final PublicCodeRepository publicCodeRepository;

    /**
     * 공통 코드 관리 (페이징 o)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Page<PublicCodeDto> selectPublicCodePageList(PublicCodeDefaultDto searchDto) throws Exception {
        return publicCodeRepository.selectPublicCodePageList(searchDto);
    }

    /**
     * 공통 코드 관리(페이징 x)
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<PublicCodeDto> selectPublicCodeList(PublicCodeDefaultDto searchDto) throws Exception {
        return publicCodeRepository.selectPublicCodeList(searchDto);
    }

    /**
     * 공통 코드 상세 정보
     * @param publicCodeDto
     * @return
     * @throws Exception
     */
    public PublicCodeDto selectPublicCodeView(PublicCodeDto publicCodeDto) throws Exception {
        return publicCodeRepository.selectPublicCode(publicCodeDto);
    }


    /**
     * 공통 코드 관리 전채 개수
     * @param searchDto
     * @return
     * @throws Exception
     */
    public Long selectPublicCodeTotalCount(PublicCodeDefaultDto searchDto) throws Exception {
        return publicCodeRepository.selectPublicCodeTotalCount(searchDto);
    }

    /**
     * 공통 코드 관리 등록
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean insertPublicCode(PublicCodeDto dto) throws Exception {
        return publicCodeRepository.insertPubliceCode(dto);
    }

    /**
     * 공통 코드 관리 수정
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean updatePublicCode(PublicCodeDto dto) throws  Exception {
        return publicCodeRepository.updatePublicCode(dto);
    }

    /**
     * 삭제
     * @param dto
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean deletePublicCode(PublicCodeDto dto) throws Exception {
        return publicCodeRepository.deletePublicCode(dto);
    }

    /**
     * 공통코드 캐쉬 처리
     * @param searchDto
     * @return
     * @throws Exception
     */
    @Cacheable(value="publicCodeCacheList" , key = "#searchDto.parentCd")
    public List<PublicCodeDto> selectPublicCodeCacheList(PublicCodeDefaultDto searchDto) throws Exception {
        searchDto.setUseYn("Y");
        return publicCodeRepository.selectPublicCodeList(searchDto);
    }
}
