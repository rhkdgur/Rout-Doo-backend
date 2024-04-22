package com.routdoo.dailyroutine.cms.util;

import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDto;
import com.routdoo.dailyroutine.cms.upload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.routdoo.dailyroutine.cms.util
 * fileName       : UploadPropertyService
 * author         : rhkdg
 * date           : 2024-04-22
 * description    : 파일 업로드 경로 가져오기
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-22        rhkdg       최초 생성
 */
@Component
@RequiredArgsConstructor
public class UploadPropertyService {

    private final PropertiesConfig propertiesConfig;

    private final FileUploadService fileUploadService;

    public String getString(String fileName,String code) throws Exception {

        if( code != null && code.startsWith("upload.")) {

            FileUploadDto dto = new FileUploadDto();
            dto.setCode(code);
            dto.setUseYn("Y");
            dto = fileUploadService.selectFileUpload(dto);
            if(dto == null) {
                return propertiesConfig.getGlobalsProperty()+propertiesConfig.getProperty(fileName,code);
            }else if(dto.getPath().isEmpty()) {
                return propertiesConfig.getGlobalsProperty()+propertiesConfig.getProperty(fileName,code);
            }else {
                return dto.getPath();
            }
        }

        return null;
    }

}
