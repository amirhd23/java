package com.sunnylab.java.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Utils {
	public static String encrypt(String plainText, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			byte[] plainTextBytes = plainText.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
			Base64.Encoder encoder = Base64.getEncoder();
			String encryptedText = encoder.encodeToString(encryptedBytes);
			return encryptedText;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String encryptedText, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] encryptedBytes = decoder.decode(encryptedText);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			String decryptedText = new String(decryptedBytes);
			return decryptedText;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
