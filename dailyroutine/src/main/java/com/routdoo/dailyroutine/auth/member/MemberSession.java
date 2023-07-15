package com.routdoo.dailyroutine.auth.member;

import com.routdoo.dailyroutine.auth.member.dto.MemberDto;

import jakarta.servlet.http.HttpSession;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member
* @fileName      : MemberSession.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.15
* @description   : 회원 세션 정보
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.15        ghgo       최초 생성
 */
public class MemberSession {
	
	public static final String member_key = "_routdoo_member_key";
	
	/**
	 * 회원 세션 등록
	 * @param session
	 * @param dto
	 */
	public static void createMemberSession(HttpSession session,MemberDto dto) {
		session.setAttribute(member_key, dto);
	}
	
	/**
	 * 회원 세션 유지 여부
	 * @param session
	 * @return
	 */
	public static boolean isSessionKeepup(HttpSession session) {
		return session.getAttribute(member_key) == null ? false : true;
	}
	
	/**
	 * 회원 정보
	 * @param session
	 * @return
	 */
	public static MemberDto getMemberSession(HttpSession session) {
		return (MemberDto)session.getAttribute(member_key);
	}
	
	/**
	 * 회원 세션 삭제
	 * @param session
	 */
	public static void clearMemberSession(HttpSession session) {
		session.removeAttribute(member_key);
	}

}
