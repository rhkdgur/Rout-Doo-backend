package com.routdoo.dailyroutine.module.chat.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.routdoo.dailyroutine.module.chat.dto.ChatMessageDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {

//	private final SimpMessagingTemplate template;
	
	/**
	 * 방에 접속
	 * @param message
	 */
	@MessageMapping(value="/chat/enter")
	public void enter(ChatMessageDto message) {
		message.setMessage(message.getWriter()+"님이 채팅방에 참여하였습니다.");
//		template.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
	}
	
	/**
	 * 내용 전달
	 * @param message
	 */
	@MessageMapping(value="/chat/message")
	public void message(ChatMessageDto message) {
//		template.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
	}
}
