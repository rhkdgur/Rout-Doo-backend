package com.routdoo.dailyroutine.auth.Typehandler;

import org.apache.ibatis.type.StringTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTypeHandler extends StringTypeHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoderTypeHandler.class);
	
	private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	/**
	 * spring 
	 * @param s 입력 문자열
	 * @return 암호화된 문자열
	 */
	public static String encode(String s) {
		return passwordEncoder.encode(s);
	}
	
	/**
	 * 인코드 패스워드 체크
	 * @param password
	 * @param encodedPassword
	 * @return
	 */
	public static boolean matches(String password, String encodedPassword) {
		
		boolean result = false;
		try {
			result = passwordEncoder.matches(password, encodedPassword);
		}catch (Exception e) {
			LOGGER.error("### encode check error : {}",e.getMessage());
		}
		
		return result;
	}

}
