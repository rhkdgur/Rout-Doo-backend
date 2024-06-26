package com.routdoo.dailyroutine.auth.jwt.dto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

public class CustomeUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private Map<String, String> element;

    @Getter
    @Setter
    private String auth = "";

    private final User user = null;

    public User getUser() {
        if (element == null) {
            return null;
        }
        return new User(element.get("username"), element.get("password"), getAuthorities());
    }

    public CustomeUserDetails(Map<String, String> element, String auth) {
        this.element = element;
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.auth));
    }

    @Override
    public String getPassword() {
        return element.get("password");
    }

    @Override
    public String getUsername() {
        return element.get("username");
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
