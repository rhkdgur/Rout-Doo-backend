package com.routdoo.dailyroutine.auth.Typehandler;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncrypterTypeHandler {

	//AES 256
	private static byte[] key = "dailyroutineroutdoo7f1290186a5d0".getBytes(StandardCharsets.UTF_8);
	private static final String ALGO = "AES";
	
	private static EncrypterTypeHandler instance = new EncrypterTypeHandler();
	
	public static EncrypterTypeHandler getInstance() {
		if(instance == null) {
			return new EncrypterTypeHandler();
		}else {
			return instance;
		}
	}

	public static void setKey(byte[] key) {
		EncrypterTypeHandler.key = key;
	}

	//암호화
 	public String encode(String plainText) throws Exception {
        return plainText != null && !plainText.isEmpty() ? Base64.getEncoder().encodeToString(this.encrypt(plainText.getBytes(StandardCharsets.UTF_8))) : "";
    }

 	//복호화
    public String decode(String encText) throws Exception {
        return encText != null &&  !encText.isEmpty() ? new String(decrypt(Base64.getDecoder().decode(encText)), StandardCharsets.UTF_8) : "";
    }

    //암호화
    private byte[] encrypt(byte[] plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGO);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plainText);
    }

    //복호화
    private byte[] decrypt(byte[] cipherText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGO);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(cipherText);
    }
    
}
