package com.routdoo.dailyroutine.auth.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routdoo.dailyroutine.auth.AuthResultCodeType;
import com.routdoo.dailyroutine.auth.AuthServiceResult;
import com.routdoo.dailyroutine.auth.admin.domain.Admin;
import com.routdoo.dailyroutine.auth.admin.dto.AdminDefaultDto;
import com.routdoo.dailyroutine.auth.admin.dto.AdminDto;
import com.routdoo.dailyroutine.auth.admin.repository.AdminRepository;
import com.routdoo.dailyroutine.auth.jwt.dto.CustomeUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

	private final AdminRepository adminRepository;
	
	/**
	 * 목록 (no paging)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public List<AdminDto> selectAdminNoLimitList(AdminDefaultDto searchDto) throws Exception {
		return adminRepository.selectAdminNoLimitList(searchDto);
	}
	
	/**
	 * 상세 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public AdminDto selectAdminView(AdminDto dto) throws Exception{
		Admin admin = adminRepository.findById(dto.getId()).orElse(null);
		if(admin != null) {
			return new AdminDto(admin);
		}
		return null;
	}
	
	/**
	 * 목록 (paging)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	public Page<AdminDto> selectAdminList(AdminDefaultDto searchDto) throws Exception {
		return adminRepository.selectAdminList(searchDto);
	}
	
	
	/**
	 * 로그인 처리
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public AuthServiceResult<AdminDto> loginAdmin(AdminDto dto) throws Exception {
		Admin admin = null;
		try {
			admin = adminRepository.findByIdAndPw(dto.getId(), dto.getPw());
			//이미 존재하는 경우 리턴
			if(admin != null) {
				return new AuthServiceResult<>(AuthResultCodeType.INFO_ALREADYID);
			}
			admin = adminRepository.save(dto.toEntity());
		}catch (Exception e) {
			return new AuthServiceResult<>(AuthResultCodeType.INFO_FAIL);
		}
		
		return new AuthServiceResult<>(AuthResultCodeType.INFO_OK,new AdminDto(admin));
	}
	
	/**
	 * 등록,수정
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public AuthServiceResult<AdminDto> saveAdmin(AdminDto dto) throws Exception {
		Admin admin = adminRepository.findById(dto.getId()).orElse(null);
		if(admin == null) {
			admin = adminRepository.save(dto.toEntity());
			if(admin != null) {
				return new AuthServiceResult<>(AuthResultCodeType.INFO_OK,new AdminDto(admin));
			}else {
				return new AuthServiceResult<>(AuthResultCodeType.INFO_FAIL,"등록에 실패하였습니다.");
			}
		}else {
			admin.changeAdmin(dto);
			return new AuthServiceResult<>(AuthResultCodeType.INFO_OK,"업데이트 되었습니다.",new AdminDto(admin));
		}
	}
	
	/**
	 * 관리자 삭제
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public AuthServiceResult<?> deleteAdmin(AdminDto dto) throws Exception {
		adminRepository.deleteById(dto.getId());
		Admin admin = adminRepository.findById(dto.getId()).orElse(null);
		if(admin == null) {
			return new AuthServiceResult<>(AuthResultCodeType.INFO_OK,"삭제되었습니다.");
		}else {
			return new AuthServiceResult<>(AuthResultCodeType.INFO_FAIL,"삭제 되지않았습니다.");
		}
	}
}
