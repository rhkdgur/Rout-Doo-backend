package com.routdoo.dailyroutine.auth.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.routdoo.dailyroutine.auth.member.domain.Member;

import lombok.Getter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt
* @fileName      : AuthDetails.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.19
* @description   : 유저 security 정보
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.19        ghgo       최초 생성
 */
@Getter
public class AuthMemberDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private final Member member;
	
	public AuthMemberDetails(Member member) {
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return member.getPw();
	}

	@Override
	public String getUsername() {
		return member.getNickname();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
