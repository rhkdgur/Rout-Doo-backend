package com.routdoo.dailyroutine.auth.jwt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.routdoo.dailyroutine.auth.jwt.AuthAdminDetails;
import com.routdoo.dailyroutine.auth.jwt.domain.JwtToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routdoo.dailyroutine.auth.admin.domain.Admin;
import com.routdoo.dailyroutine.auth.admin.repository.AdminRepository;
import com.routdoo.dailyroutine.auth.jwt.domain.JwtTokenEntity;
import com.routdoo.dailyroutine.auth.jwt.dto.CustomeUserDetails;
import com.routdoo.dailyroutine.auth.jwt.repository.JwtTokenEntityRepository;
import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.auth.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt.service
* @fileName      : JwtTokenService.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.30
* @description   : jwt 리포지토리
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.30        ghgo       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class JwtTokenService {
	
	private final JwtTokenEntityRepository jwtTokenEntityRepository;

	private final MemberRepository memberRepository;
	
	private final AdminRepository adminRepository; 
	
	
	@Transactional
	@CacheEvict(value={ "admin_login_info", "member_login_info", "jwt_token_info"})
	public void save(JwtTokenEntity entity) throws Exception {
		jwtTokenEntityRepository.save(entity);
	}
	
	@Transactional
	@CacheEvict(value={ "admin_login_info", "member_login_info","jwt_token_info"})
	public void delete(String id) throws Exception {
		jwtTokenEntityRepository.deleteById(id);
	}

	public List<JwtTokenEntity> findList() throws Exception {
		return jwtTokenEntityRepository.findAll();
	}

//	@Cacheable(value="jwt_token_info", key="#id")
	public JwtTokenEntity find(String id) {
		return jwtTokenEntityRepository.findById(id).orElse(null);
	}
	
	/**
	 * Jwt 권한 회원 정보 조
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value="admin_login_info", key="#loginId")
	public AuthAdminDetails loadAdminByUsername(String loginId) throws Exception {
		Admin admin = adminRepository.findById(loginId).orElseThrow(() -> new UsernameNotFoundException(loginId + "-> 존재 하지 않음"));
		return new AuthAdminDetails(admin);
	}
	
	
	/**
	 * Jwt 권한 회원 정보 조
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value="member_login_info", key="#loginId")
	public CustomeUserDetails loadUserByUsername(String loginId) throws Exception {
		Member member = memberRepository.findById(loginId).orElseThrow(() -> new UsernameNotFoundException(loginId + "-> 존재 하지 않음"));
		Map<String,String> map = new HashMap<>();
		map.put("username", member.getId());
		map.put("password", member.getPw());
		return new CustomeUserDetails(map,"USER");
	}

}
