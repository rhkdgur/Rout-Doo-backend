package com.routdoo.dailyroutine.module.routine.service;

import lombok.Getter;

@Getter
public enum RoutineDayType {

	DAY("당일"),
	LONG_DAY("장기");
	
	private String display = "";
	
	RoutineDayType(String display){
		this.display = display;
	}

}
