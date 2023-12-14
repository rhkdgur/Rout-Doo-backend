package com.routdoo.dailyroutine.module.place.service;

public enum PlaceRemoveType {

	NONE("요청"),

	OK("승인"),
	REJECT("거절");
	
	private String display = "";
	
	PlaceRemoveType(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return this.display;
	}
}
