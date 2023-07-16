package com.routdoo.dailyroutine.auth.admin.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.routdoo.dailyroutine.auth.admin.domain.Admin;
import com.routdoo.dailyroutine.auth.admin.domain.QAdmin;
import com.routdoo.dailyroutine.auth.admin.dto.AdminDefaultDto;
import com.routdoo.dailyroutine.auth.admin.dto.AdminDto;
import com.routdoo.dailyroutine.auth.admin.repository.AdminCustomRepository;
import com.routdoo.dailyroutine.common.BaseAbstractRepositoryImpl;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.admin.repository.impl
* @fileName      : AdminCustomRepostiroyImpl.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.16
* @description   : 관리자 custom repository impl
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.16        ghgo       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class AdminCustomRepostiroyImpl extends BaseAbstractRepositoryImpl implements AdminCustomRepository{
	
	
	private BooleanBuilder commonQuery(AdminDefaultDto searchDto) throws Exception {
		QAdmin qAdmin = QAdmin.admin;
		BooleanBuilder sql = new BooleanBuilder();
		if(searchDto.getSstring() != null && !searchDto.getSstring().isEmpty()) {
			
		}
		
		return sql;
	}
	
	/**
	 * 관리자 목록 (no paging)
	 * @throws Exception 
	 */
	@Override
	public List<AdminDto> selectAdminNoLimitList(AdminDefaultDto searchDto) throws Exception {
		QAdmin qAdmin = QAdmin.admin;
		
		List<Admin> list = jpaQuery.selectFrom(qAdmin).where(commonQuery(searchDto)).fetch();
		
		return list.stream().map(x->new AdminDto(x)).collect(Collectors.toList());
	}

	/**
	 * 관리자 목록(paging)
	 */
	@Override
	public Page<AdminDto> selectAdminList(AdminDefaultDto searchDto) throws Exception{
		QAdmin qAdmin = QAdmin.admin;
		
		List<Admin> list = jpaQuery.selectFrom(qAdmin)
								.where(commonQuery(searchDto))
								.offset(searchDto.getPageable().getOffset())
								.limit(searchDto.getPageable().getPageSize())
								.fetch();
		
		Long cnt = jpaQuery.select(qAdmin.count()).from(qAdmin)
							.where(commonQuery(searchDto)).fetchFirst();
		
		
		return new PageImpl<AdminDto>(list.stream().map(x->new AdminDto(x)).collect(Collectors.toList()),searchDto.getPageable(),cnt);
	}

	
}
