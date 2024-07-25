package com.routdoo.dailyroutine.cms.publiccode.web;

import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDefaultDto;
import com.routdoo.dailyroutine.cms.publiccode.dto.PublicCodeDto;
import com.routdoo.dailyroutine.cms.publiccode.service.PublicCodeService;
import com.routdoo.dailyroutine.common.web.BaseModuleController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.cms.publiccode.web
 * fileName       : PublicUserController
 * author         : rhkdg
 * date           : 2023-12-20
 * description    : 공통코드 사용자 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-20        rhkdg       최초 생성
 */
@Tag(name = "공통코드 사용자 컨트롤러")
@RestController
@RequiredArgsConstructor
public class PublicCodeUserController extends BaseModuleController {

    private final PublicCodeService publicCodeService;

    /**
     * 대분류 카테고리 목록 조회
     * @return
     * @throws Exception
     */
    @Operation(summary = "전체 대분류 카테고리 목록 조회(따로 파라미터가 필요없음)")
    @GetMapping(API_URL+"/public/code/tops")
    public Map<String,Object> selectPublicCodeList() throws Exception {

        modelMap = new LinkedHashMap<>();

        PublicCodeDefaultDto searchDto = new PublicCodeDefaultDto();
        searchDto.setUseYn("Y");
        searchDto.setParentCd("_TOP");
        List<PublicCodeDto> resultList = publicCodeService.selectPublicCodeCacheList(searchDto);
        modelMap.put("codeList",resultList);

        return modelMap;
    }

}
