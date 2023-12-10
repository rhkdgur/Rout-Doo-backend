package com.routdoo.dailyroutine.cms.file.service;

import com.routdoo.dailyroutine.cms.file.dto.CmsFileDefaultDto;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileDto;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileSupport;
import com.routdoo.dailyroutine.cms.file.exception.ClassTypeMissMatchException;
import com.routdoo.dailyroutine.cms.file.repository.CmsFileRepository;
import com.routdoo.dailyroutine.common.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
@Transactional(readOnly = true)
public class CmsFileService implements CmsFileDetailService{

	private final CmsFileRepository cmsFileRepository;

	private final FileUploadUtil fileUploadUtil;


	/**
	 * 파일 목록 조회
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<CmsFileDto> selectCmsFileList(CmsFileDefaultDto searchDto) throws Exception {
		return cmsFileRepository.selectCmsFileList(searchDto);
	}

	/**
	 * 파일 상세 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public CmsFileDto selectCmsFile(CmsFileDto dto) throws Exception {
		return cmsFileRepository.selectCmsFile(dto);
	}


	/**
	 * 파일 등록
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean insertCmsFile(Object obj) throws Exception {

		return true;
	}


	@Transactional
	@Override
	public <T extends CmsFileSupport<?>> boolean processFileCreate(T obj) throws Exception {

		CmsFileDto[] cmsFileDtoList = null;
		if(!support(obj.getClass())){
			return false;
		}

		try{
			//예외 발생시 삭제 처리 파일리스트
			List<File> exceptionFileDeleteList = new ArrayList<>();
			//이전 존재하던 파일 삭제여부에 따른 파일 리스트
			List<File> existFileDeleteList = new ArrayList<>();

			cmsFileDtoList = (CmsFileDto[]) obj.getCmsFileList();

			for(CmsFileDto cmsFileDto : cmsFileDtoList){
				if(!cmsFileDto.getMultiPartFile().isEmpty()){
					MultipartFile multipartFile = cmsFileDto.getMultiPartFile();
					if(fileUploadUtil.validateUploadFile(multipartFile,fileUploadUtil.ALLOW_EXTS)){
						String saveFile = fileUploadUtil.saveUploadFile("uploadpath",multipartFile,null,false);
						cmsFileDto.setOriginalFileName(multipartFile.getOriginalFilename());
						cmsFileDto.setSaveFileName(saveFile);
						cmsFileDto.setFileSize(multipartFile.getSize()+"");
						cmsFileDto.setExtension(multipartFile.getOriginalFilename().split("[.]")[1]);
						cmsFileDto.setTagName("");
					}
				}
			}

		}catch (Exception e){

		}

		return false;
	}

	@Transactional
	@Override
	public <T extends CmsFileSupport<?>> boolean processFileUpdate(T obj) throws Exception {
		return false;
	}

	@Transactional
	@Override
	public <T extends CmsFileSupport<?>> boolean proccessFileDelete(T obj) throws Exception {
		return false;
	}

	/**
	 * CmsFileSupport를 상속하고 있는지 체크하는 함수
	 * @param classDto
	 * @return
	 */
	private boolean support(Class<?> classDto)  {
		if(CmsFileSupport.class.isAssignableFrom(classDto)){
			return true;
		}
		return false;
	}
}
