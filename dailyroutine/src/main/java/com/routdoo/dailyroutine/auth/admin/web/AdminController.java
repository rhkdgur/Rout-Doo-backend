package com.routdoo.dailyroutine.auth.admin.web;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.routdoo.dailyroutine.auth.AuthResultCodeType;
import com.routdoo.dailyroutine.auth.AuthServiceResult;
import com.routdoo.dailyroutine.auth.admin.dto.AdminDefaultDto;
import com.routdoo.dailyroutine.auth.admin.dto.AdminDto;
import com.routdoo.dailyroutine.auth.admin.service.AdminService;
import com.routdoo.dailyroutine.common.web.BaseController;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.admin.web
* @fileName      : AdminController.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.16
* @description   : 관리자 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.16        ghgo       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class AdminController extends BaseController{

	private final AdminService adminService;
	
	/**
	 * 관리자 목록 조회(페이징 포함)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(MGN_URL+"/admin/list")
	public Map<String,Object> selectAdminList(AdminDefaultDto searchDto) throws Exception {
		
		//목록 조회
		Page<AdminDto> resultList = adminService.selectAdminList(searchDto);
		modelMap.put("adminList", resultList);
		
		return modelMap;
	}

	/**
	 * 관리자 상세 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@GetMapping(MGN_URL+"/admin/view")
	public Map<String,Object> selectAdminView(AdminDto dto) throws Exception {
		
		dto = adminService.selectAdminView(dto);
		modelMap.put("admin", dto);
		
		return modelMap;
	}
	
	/**
	 * 관리자 등록
	 * @param dto
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/admin/act/ins")
	public ResponseEntity<?> createAdmin(AdminDto dto, SessionStatus status) throws Exception {
		
		try {
			AdminDto checkDto = adminService.selectAdminView(dto);
			if(checkDto != null) {
				return new ResponseEntity<>("이미 사용중인 아이디 입니다.",HttpStatus.ALREADY_REPORTED);
			}
			
			AuthServiceResult<?> result = adminService.saveAdmin(checkDto);
			if(!AuthResultCodeType.INFO_OK.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>(result.getMessage(),HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
			logger.error("### insert admin error");
			return new ResponseEntity<>("관리자 등록시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		status.setComplete();
		return new ResponseEntity<>("등록 되었습니다.",HttpStatus.OK);
	}
	
	
	/**
	 * 관리자 수정
	 * @param dto
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/admin/act/upd")
	public ResponseEntity<?> updateAdmin(AdminDto dto, SessionStatus status) throws Exception {
		
		AuthServiceResult<?> result = adminService.saveAdmin(dto);
		
		status.setComplete();
		return new ResponseEntity<>("수정 되었습니다.",HttpStatus.OK);
	}
	
	/**
	 * 관리자 삭제
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping(MGN_URL+"/admin/act/del")
	public ResponseEntity<?> deleteAdmin(@RequestParam("id") String id) throws Exception {
		
		AdminDto dto = new AdminDto();
		dto.setId(id);
		dto = adminService.selectAdminView(dto);
		
		try {
			AuthServiceResult<?> result = adminService.deleteAdmin(dto);
			if(AuthResultCodeType.INFO_FAIL.name().equals(result.getCodeType().name())) {
				return new ResponseEntity<>("삭제에 실패하였습니다.",HttpStatus.NOT_MODIFIED);
			}
		}catch (Exception e) {
			logger.error("### delete member error");
			return new ResponseEntity<>("삭제시 오류가 발생했습니다.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("삭제 되었습니다.",HttpStatus.OK);
	}
}
