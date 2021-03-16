package com.jacknie.example;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public abstract class CryptoUtils {

	private CryptoUtils() {
		throw new UnsupportedOperationException();
	}

	public static String encrypt(RSAPrivateKey privateKey, String text) throws Exception {
		var cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		var encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
		var encoded = Base64.getUrlEncoder().encode(encrypted);
		return new String(encoded, StandardCharsets.UTF_8);
	}

	public static String decrypt(RSAPublicKey publicKey, String text) throws Exception {
		var decoded = Base64.getUrlDecoder().decode(text.getBytes(StandardCharsets.UTF_8));
		var cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		var decrypted = cipher.doFinal(decoded);
		return new String(decrypted, StandardCharsets.UTF_8);
	}

	public static void main(String[] args) {
		var encoded = Base64.getUrlEncoder().encode("test".getBytes(StandardCharsets.UTF_8));
		System.out.println(new String(encoded, StandardCharsets.UTF_8));
	}

}
