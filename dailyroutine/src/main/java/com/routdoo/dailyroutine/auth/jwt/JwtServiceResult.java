package com.routdoo.dailyroutine.auth.jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member
* @fileName      : MemberServiceResult.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.13
* @description   : JWT 토큰 결과 처리 클래스
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.13        ghgo       최초 생성
 */
@Getter @Setter
public class JwtServiceResult<T> {
	
	private JwtResultCodeType codeType;
	
	private String message;
	
	private T element;
	
	public JwtServiceResult(JwtResultCodeType codeType, String message, T element){
		this.codeType = codeType;
		this.message = message;
		this.element = element;
	}
	
	public JwtServiceResult(JwtResultCodeType codeType, String message) {
		this.codeType = codeType;
		this.message = message;
	}
	
	public JwtServiceResult(JwtResultCodeType codeType, T element) {
		this.codeType = codeType;
		this.element = element;
		this.message = codeType.getDisplay();
	}
	
	public JwtServiceResult(JwtResultCodeType codeType) {
		this.codeType = codeType;
		this.message = codeType.getDisplay();
	}
}
