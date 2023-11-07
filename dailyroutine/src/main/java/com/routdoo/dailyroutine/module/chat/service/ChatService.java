package com.routdoo.dailyroutine.module.chat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.routdoo.dailyroutine.module.chat.dto.ChatRoomDto;

import jakarta.annotation.PostConstruct;

@Service
public class ChatService {

	//메모리 테스트
	private Map<String,ChatRoomDto> chatRoomtDtoMap;
	
	@PostConstruct
	private void init() {
		chatRoomtDtoMap = new LinkedHashMap<String, ChatRoomDto>();
	}
	
	/**
	 * 전체 방 조회
	 * @return
	 */
	public List<ChatRoomDto> findAllRooms(){
		//채팅방 생성 순서 최근 순으로 반환
		List<ChatRoomDto> result = new ArrayList<>(chatRoomtDtoMap.values());
		Collections.reverse(result);
		return result;
	}
	
	/**
	 * 상세 방 조회
	 * @param id
	 * @return
	 */
	public ChatRoomDto findRoomById(String id) {
		return chatRoomtDtoMap.get(id);
	}
	
	/**
	 * 방만들기
	 * 
	 * @param name
	 * @return
	 */
	public ChatRoomDto createChatRoomDto(String name) {
		ChatRoomDto room = ChatRoomDto.create(name);
		chatRoomtDtoMap.put(room.getRoomId(), room);
		return room;
	}
}
