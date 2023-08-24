package com.routdoo.dailyroutine.module.routine.service;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.service
* @fileName      : RoutineSearchType.java
* @author        : Gwang hyeok Go
* @date          : 2023.08.13
* @description   : 직접검색인지 , 장소 검색인지 타입 설정
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.08.13        ghgo       최초 생성
 */
public enum RoutineWriteType {

	DIRECT("직접입력"),
	SEARCH("검색입력");
	
	private String display = "";
	
	RoutineWriteType(String display){
		this.display = display;
	}
	
	public String getDisplay() {
		return this.display;
	}
}
