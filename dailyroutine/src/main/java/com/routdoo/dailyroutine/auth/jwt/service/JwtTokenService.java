package com.routdoo.dailyroutine.auth.jwt.service;

import java.util.HashMap;
import java.util.Map;

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
	public void save(JwtTokenEntity entity) throws Exception {
		jwtTokenEntityRepository.save(entity);
	}
	
	@Transactional
	public void delete(String id) throws Exception {
		jwtTokenEntityRepository.deleteById(id);
	}
	
	public JwtTokenEntity find(String id) {
		return jwtTokenEntityRepository.findById(id).orElse(null);
	}
	
	/**
	 * Jwt 권한 회원 정보 조
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	public CustomeUserDetails loadAdminByUsername(String loginId) throws Exception {
		Admin admin = adminRepository.findById(loginId).orElse(null);
		if(admin == null) {
			throw new Exception();
		}
		Map<String,String> map = new HashMap<>();
		map.put("username", admin.getName());
		map.put("id", admin.getId());
		map.put("password", admin.getPw());
		return new CustomeUserDetails(map,"ADMIN");
	}
	
	
	/**
	 * Jwt 권한 회원 정보 조
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	public CustomeUserDetails loadUserByUsername(String loginId) throws Exception {
		Member member = memberRepository.findById(loginId).orElse(null);
		if(member == null) {
			throw new Exception();
		}
		Map<String,String> map = new HashMap<>();
		map.put("username", member.getId());
		map.put("password", member.getPw());
		return new CustomeUserDetails(map,"USER");
	}
}
