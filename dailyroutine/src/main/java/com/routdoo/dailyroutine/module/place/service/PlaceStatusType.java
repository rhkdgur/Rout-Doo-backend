package com.routdoo.dailyroutine.module.place.service;

public enum PlaceStatusType {

	Y("사용함"),
	N("사용안함");
	
	private String display = "";
	
	PlaceStatusType(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return this.display;
	}
}
