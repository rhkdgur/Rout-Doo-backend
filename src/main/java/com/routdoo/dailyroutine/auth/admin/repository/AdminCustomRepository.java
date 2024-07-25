package com.routdoo.dailyroutine.auth.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.routdoo.dailyroutine.auth.admin.dto.AdminDefaultDto;
import com.routdoo.dailyroutine.auth.admin.dto.AdminDto;

public interface AdminCustomRepository {

	/**
	 * 관리자 목록 (no paging)
	 * @param searchDto
	 * @return
	 */
	List<AdminDto> selectAdminNoLimitList(AdminDefaultDto searchDto) throws Exception;
	
	/**
	 * 관리자 목록(paging)
	 * @param searchDto
	 * @return
	 */
	Page<AdminDto> selectAdminList(AdminDefaultDto searchDto) throws Exception;
}
 