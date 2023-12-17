package com.routdoo.dailyroutine.module.routine.service;

import lombok.Getter;

/**
 * 일정 공개 비공개 enum
 */
@Getter
public enum RoutineRangeConfigType {

	PUBLIC("공개"),
	PRIVATE("비공개");
	
	private String display = "";
	
	RoutineRangeConfigType(String display) {
		this.display = display;
	}

}
