package com.routdoo.dailyroutine.module.routine;

import com.routdoo.dailyroutine.auth.AuthResultCodeType;

import lombok.Getter;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member
* @fileName      : MemberServiceResult.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.13
* @description   : 회원 결과 처리 클래스
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.13        ghgo       최초 생성
 */
@Getter @Setter
public class RoutineServiceResult<T> {
	
	private RoutineResultCodeType codeType;
	
	private String message;
	
	private T element;
	
	public RoutineServiceResult(RoutineResultCodeType codeType, String message, T element){
		this.codeType = codeType;
		this.message = message;
		this.element = element;
	}
	
	public RoutineServiceResult(RoutineResultCodeType codeType, String message) {
		this.codeType = codeType;
		this.message = message;
	}
	
	public RoutineServiceResult(RoutineResultCodeType codeType, T element) {
		this.codeType = codeType;
		this.element = element;
		this.message = codeType.getDisplay();
	}
	
	public RoutineServiceResult(RoutineResultCodeType codeType) {
		this.codeType = codeType;
		this.message = codeType.getDisplay();
	}
}
