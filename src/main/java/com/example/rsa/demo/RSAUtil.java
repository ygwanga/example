package com.example.rsa.demo;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSAUtil {
	
	static String RSA = "RSA";
	
	public static void getKeyPair(String filePath) throws Exception {
		KeyPairGenerator keyPairGenerator = null;
		
		keyPairGenerator = KeyPairGenerator.getInstance(RSA);
		
		String path = RSAUtil.class.getClassLoader().getResource("").getPath();
		System.out.println(path);
		
	}
	
	public static void main(String[] args) throws Exception {
		getKeyPair("");
	}
	
}
