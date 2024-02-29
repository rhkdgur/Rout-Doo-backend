package com.routdoo.dailyroutine.auth.jwt;

import java.util.Collection;
import java.util.List;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

	private final User user = null;

	@Getter
	@Setter
	private String auth = "ADMIN";
	
	public AuthAdminDetails(Admin admin) {
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.auth));
	}

	@Override
	public String getPassword() {
		return admin.getPw();
	}

	@Override
	public String getUsername() {
		return admin.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
