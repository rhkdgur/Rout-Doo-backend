package com.routdoo.dailyroutine.auth.member;

public enum MemberResultCodeType {

	INFO_OK("정상입니다."),
	INFO_FAIL("실패입니다."),
	INFO_NOMATCH("아이디 또는 패스워드가 일치하지 않습니다."),
	INFO_ALREADYID("이미 존재하는 아이디입니다."),
	INFO_NOTFOUND("해당 정보를 찾을 수 없습니다.");
	
	private String display = "";

	MemberResultCodeType(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return this.display;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}
	
}
