package com.routdoo.dailyroutine.cms.file.service;

import com.routdoo.dailyroutine.cms.file.CmsFileThreadLocalHolder;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileDefaultDto;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileDto;
import com.routdoo.dailyroutine.cms.file.dto.CmsFileSupport;
import com.routdoo.dailyroutine.cms.file.exception.DoNotMatchExtException;
import com.routdoo.dailyroutine.cms.file.repository.CmsFileRepository;
import com.routdoo.dailyroutine.cms.util.UploadPropertyService;
import com.routdoo.dailyroutine.common.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	private final UploadPropertyService uploadPropertyService;


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


	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public <T extends CmsFileSupport<?>> boolean processFileCreate(T obj) throws Exception {

		MultipartHttpServletRequest multirequest = CmsFileThreadLocalHolder.getRequest();

		List<CmsFileDto> cmsFileList = new ArrayList<>();
		if(!support(obj.getClass())){
			return false;
		}
		//예외 발생시 삭제 처리 파일리스트
		List<File> exceptionFileDeleteList = new ArrayList<>();
		//이전 존재하던 파일 삭제여부에 따른 파일 리스트
		List<File> existFileDeleteList = new ArrayList<>();
		try{

			String uploadPath = uploadPropertyService.getString(obj.getUploadCodePath(),obj.getUploadCode());

			MultiValueMap<String, MultipartFile> files = multirequest.getMultiFileMap();

			//첨부 파일 가능한지 먼저 체크
			for(Map.Entry<String, List<MultipartFile>> entry : files.entrySet()){
				MultipartFile multiFile = entry.getValue().get(0);
				String saveFileName = multiFile.getOriginalFilename();
				//첨부 파일 확장명 체크
				if(!fileUploadUtil.validateUploadFile(saveFileName,fileUploadUtil.ALLOW_EXTS)){
					throw new DoNotMatchExtException("해당 파일은 첨부할 수 없는 파일 형식입니다.");
				}
			}

			//이전 가지고 있던 파일 가져오기
			CmsFileDefaultDto cmsFileDefaultDto = new CmsFileDefaultDto();
			cmsFileDefaultDto.setParentIdx(obj.getParentIdx());
			cmsFileDefaultDto.setUploadCode(obj.getUploadCode());
			List<CmsFileDto> beforeList = this.selectCmsFileList(cmsFileDefaultDto);
			Map<Long,CmsFileDto> existMap = new LinkedHashMap<>();
			for(CmsFileDto cmsFileDto : beforeList){
				existMap.put(cmsFileDto.getIdx(),cmsFileDto);
			}

			//파일 저장
			for(Map.Entry<String,List<MultipartFile>> entry : files.entrySet()){
				String formName = entry.getKey();
				String modifyIdx = multirequest.getParameter("_modify"+formName);
				String deleteIdx = multirequest.getParameter("_delete"+formName);

				MultipartFile multipartFile = entry.getValue().get(0);

				if(!multipartFile.isEmpty()){
					CmsFileDto cmsFileDto = new CmsFileDto();
					cmsFileDto.setParentIdx(obj.getParentIdx());
					cmsFileDto.setUploadCode(obj.getUploadCode());
					cmsFileDto.setOriginalFileName(multipartFile.getOriginalFilename());
					String saveFileName = fileUploadUtil.saveUploadFile(uploadPath, multipartFile,null,false);
					exceptionFileDeleteList.add(new File(uploadPath,saveFileName));
					cmsFileDto.setSaveFileName(saveFileName);
					cmsFileDto.setFileSize(multipartFile.getSize()+"");
					cmsFileDto.setTagName(formName);
					cmsFileDto.setSaveFilePath(uploadPath);
					cmsFileDto.setExtension(saveFileName.substring(0,saveFileName.lastIndexOf(".")+1));

					//이미 파일이 존재했던 데이터일 경우
					if(modifyIdx != null){
						cmsFileDto.setIdx(Long.valueOf(modifyIdx));
						cmsFileRepository.updateCmsFile(cmsFileDto);
						CmsFileDto tempFileDto = existMap.get(modifyIdx);
						if(tempFileDto != null){
							existFileDeleteList.add(new File(uploadPath,tempFileDto.getSaveFileName()));
						}
					}else {
						cmsFileRepository.insertCmsFile(cmsFileDto);
					}
				}else {
					//해당
					if(modifyIdx != null && !modifyIdx.isEmpty() && existMap.containsKey(modifyIdx)){
						if(deleteIdx != null && existMap.containsKey(deleteIdx)){
							CmsFileDto deleteMap = existMap.get(deleteIdx);
							if(deleteMap != null){
								existFileDeleteList.add(new File(uploadPath,deleteMap.getSaveFileName()));
								cmsFileRepository.deleteCmsFile(deleteMap);
							}
						}
					}
				}
			}
			fileUploadUtil.deleteFile(existFileDeleteList);
		}catch (Exception e){
			e.printStackTrace();
			fileUploadUtil.deleteFile(exceptionFileDeleteList);
			System.out.println("### cms file insert error  : "+e.getMessage());
		}

		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public <T extends CmsFileSupport<?>> boolean processFileUpdate(T obj) throws Exception {
		return this.processFileCreate(obj);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public <T extends CmsFileSupport<?>> boolean proccessFileDelete(T obj) throws Exception {

		CmsFileDefaultDto cmsFileDefaultDto = new CmsFileDefaultDto();
		cmsFileDefaultDto.setParentIdx(obj.getParentIdx());
		cmsFileDefaultDto.setUploadCode(obj.getUploadCode());

		List<CmsFileDto> cmsFileDtoList = this.selectCmsFileList(cmsFileDefaultDto);

		for(CmsFileDto cmsFileDto : cmsFileDtoList){
			cmsFileRepository.deleteCmsFile(cmsFileDto);
		}

		for(CmsFileDto cmsFileDto : cmsFileDtoList){
			fileUploadUtil.deleteFile("",cmsFileDto.getSaveFileName());
		}

		return true;
	}

	/**
	 * CmsFileSupport를 상속하고 있는지 체크하는 함수
	 * @param classDto
	 * @return
	 */
	private boolean support(Class<?> classDto) {
		if (CmsFileSupport.class.isAssignableFrom(classDto)) {
			return true;
		}
		return false;
	}
}
