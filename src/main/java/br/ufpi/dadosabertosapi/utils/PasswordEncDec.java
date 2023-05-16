package br.ufpi.dadosabertosapi.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class PasswordEncDec {
	
	private static String keyString = "ThWmZq4t7w!z%C*F-J@NcRfUjXn2r5u8";
	
	
	
	public static String encryptPass(String text) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		
		byte[] x = Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest(keyString.getBytes("UTF-8")), 16);
		
		Key key = new SecretKeySpec(x, "AES");
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
		
	}
	
	public static String decryptPass(String text) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		
		byte[] x = Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest(keyString.getBytes("UTF-8")), 16);
		
		Key key = new SecretKeySpec(x, "AES");
		
		Cipher cipher = Cipher.getInstance("AES");
		
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
		
		
	}

}
