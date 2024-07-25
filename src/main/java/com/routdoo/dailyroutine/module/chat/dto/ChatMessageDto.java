package com.routdoo.dailyroutine.module.chat.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/***
 * 
* @packageName   : com.routdoo.dailyroutine.module.chat
* @fileName      : ChatDTO.java
* @author        : Gwang hyeok Go
* @date          : 2023.11.07
* @description   : 소켓 처리
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.11.07        ghgo       최초 생성
 */
@Getter
@Setter
public class ChatMessageDto {
	
	private String roomId;

	private String writer;
	
	private String message;
	
	private LocalDateTime createDate;
	
}
