package com.routdoo.dailyroutine.module.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//stomp의 접속주소
		registry.addEndpoint("/stomp/chat").setAllowedOrigins("http://localhost:8080").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		//클라이언트의 send요청 url
		registry.setApplicationDestinationPrefixes("/pub");
		
		//sub하는 클라이언트에게 메시지 전달
		registry.enableSimpleBroker("/sub");
		
	}
	
}
