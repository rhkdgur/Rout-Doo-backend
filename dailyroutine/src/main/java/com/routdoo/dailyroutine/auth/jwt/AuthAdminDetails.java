package com.routdoo.dailyroutine.auth.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.routdoo.dailyroutine.auth.admin.domain.Admin;

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
public class AuthAdminDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private final Admin admin;
	
	public AuthAdminDetails(Admin admin) {
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return admin.getPw();
	}

	@Override
	public String getUsername() {
		return admin.getName();
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
