package com.routdoo.dailyroutine.module.chat.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {

	private String roomId;
	
	private String name;
	
	//WebScoket connetion session
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	//방생성
	public static ChatRoomDto create(String name) {
		ChatRoomDto room = new ChatRoomDto();
		room.roomId = UUID.randomUUID().toString();
		room.name = name;
		return room;
	}
}
