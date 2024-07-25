package com.routdoo.dailyroutine.auth.jwt;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt
* @fileName      : JwtResultCodeType.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.19
* @description   : JWT enum type
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.19        ghgo       최초 생성
 */
public enum JwtResultCodeType {

	TOKEN_OK("정상"),
	INVALID_TOKEN("잘못된 JWT 서명입니다."),
	EXPIRED_TOKEN("만료된 JWT 토큰입니다."),
	TOKEN_NOTSUPPORT("지원되지 않는 JWT 토큰입니다."),
	TOKEN_WRONG("JWT 토큰이 잘못 되었습니다.");
	
	private String display = "";

	JwtResultCodeType(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return this.display;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}
	
}
