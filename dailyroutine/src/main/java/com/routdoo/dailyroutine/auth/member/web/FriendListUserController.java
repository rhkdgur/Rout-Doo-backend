package com.routdoo.dailyroutine.auth.member.web;

import org.springframework.web.bind.annotation.RestController;

import com.routdoo.dailyroutine.auth.member.service.FriendListService;

import lombok.RequiredArgsConstructor;

/**
 * 친구목록 컨트롤러 
 * @author GAMJA
 *
 */
@RestController
@RequiredArgsConstructor
public class FriendListUserController {

	private final FriendListService friendListService;
	
	
}
