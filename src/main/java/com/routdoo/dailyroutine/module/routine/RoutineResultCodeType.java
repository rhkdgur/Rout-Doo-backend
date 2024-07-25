package com.routdoo.dailyroutine.module.routine;

public enum RoutineResultCodeType {

	OK("정상입니다."),
	FAIL("실패입니다.");
	
	private String display = "";

	RoutineResultCodeType(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return this.display;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}
	
}
