package com.routdoo.dailyroutine.module.routine.service;

public enum RoutineDayType {

	DAY("당일"),
	LONGDAY("장기");
	
	private String display = "";
	
	RoutineDayType(String display){
		this.display = display;
	}
	
	public String getDisplay() {
		return this.display;
	}
}
