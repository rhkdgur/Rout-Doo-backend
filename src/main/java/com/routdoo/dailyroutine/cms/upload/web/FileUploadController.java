package com.routdoo.dailyroutine.cms.upload.web;

import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDefaultDto;
import com.routdoo.dailyroutine.cms.upload.dto.FileUploadDto;
import com.routdoo.dailyroutine.cms.upload.dto.FileUploadRequest;
import com.routdoo.dailyroutine.cms.upload.service.FileUploadService;
import com.routdoo.dailyroutine.common.web.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 등록
     * @return
     * @throws Exception
     */
    @PostMapping(MGN_URL+"/file/upload/ins")
    public ResponseEntity<?> insertFileUpload(@RequestBody @Valid FileUploadRequest fileUploadRequest) throws Exception {
        try{
            fileUploadService.saveFileUpload(FileUploadDto.requestOf(fileUploadRequest));
        }catch (Exception e) {
            logger.error("### insert file upload error : {}",e.getMessage());
            modelMap.put("msg","파일 경로 등록시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","등록 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }

    /**
     * 수정
     * @return
     * @throws Exception
     */
    @PutMapping(MGN_URL+"/file/upload/upd")
    public ResponseEntity<?> updateFileUpload(@RequestBody @Valid FileUploadRequest fileUploadRequest) throws Exception {
        try{
            fileUploadService.saveFileUpload(FileUploadDto.requestOf(fileUploadRequest));
        }catch (Exception e) {
            logger.error("### update file upload error : {}",e.getMessage());
            modelMap.put("msg","파일 경로 수정시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","수정 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }

    /**
     * 삭제
     * @param code
     * @return
     * @throws Exception
     */
    @DeleteMapping(MGN_URL+"/file/upload/del")
    public ResponseEntity<?> deleteFileUpload(@RequestParam("code") String code) throws Exception {
        try{
            FileUploadDto fileUploadDto = new FileUploadDto();
            fileUploadDto.setCode(code);
            fileUploadService.deleteFileUpload(fileUploadDto);
        }catch (Exception e) {
            logger.error("### delete file upload error : {}",e.getMessage());
            modelMap.put("msg","파일 경로 삭제시 오류가 발생했습니다.");
            return new ResponseEntity<>(modelMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        modelMap.put("msg","삭제 되었습니다.");
        return new ResponseEntity<>(modelMap,HttpStatus.OK);
    }
}
