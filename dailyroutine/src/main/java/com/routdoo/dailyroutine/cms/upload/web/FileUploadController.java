package com.routdoo.dailyroutine.cms.upload.web;

import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDefaultDto;
import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDto;
import com.routdoo.dailyroutine.cms.upload.service.FileUploadService;
import com.routdoo.dailyroutine.common.web.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.cms.upload.web
 * fileName       : FileUploadController
 * author         : rhkdg
 * date           : 2024-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        rhkdg       최초 생성
 */
@Tag(name="파일 업로드 경로 컨트롤러")
@RestController
@RequiredArgsConstructor
public class FileUploadController extends BaseController {

    private final FileUploadService fileUploadService;

    /**
     * 파일 업로드 경로 목록
     * @param searchDto
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/file/upload/list")
    public Map<String,Object> selectFileUploadList(FileUploadDefaultDto searchDto) throws Exception {

        Page<FileUploadDto> resultList = fileUploadService.selectFileUploadPageList(searchDto);
        modelMap.put("resultList",resultList);

        return modelMap;
    }

    /**
     * 파일 업로드 상세 정보
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping(MGN_URL+"/file/upload/view/{code}")
    public Map<String,Object> selectFileUpload(@PathVariable String code) throws Exception {

        FileUploadDto fIleUPloadDto = new FileUploadDto();
        fIleUPloadDto.setCode(code);
        fIleUPloadDto = fileUploadService.selectFileUpload(fIleUPloadDto);
        modelMap.put("fileUploadDto",fIleUPloadDto);

        return modelMap;
    }


    @PostMapping(MGN_URL+"/file/upload/ins")
    public ResponseEntity<?> insertFileUpload() throws Exception {
        return null;
    }
}
