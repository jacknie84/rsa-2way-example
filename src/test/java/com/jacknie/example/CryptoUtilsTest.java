package com.jacknie.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class CryptoUtilsTest {

	private static final String KEYSTORE_PASS = "INSERT_YOUR_KEYSTORE_PASSWORD";
	private static final String ALIAS = "INSERT_YOUR_ALIAS";
	private static final Path KEYSTORE_FILE_PATH = Paths.get(System.getProperty("user.dir"), ".keystore");
	private static final Path PUBLIC_KEY_FILE_PATH = Paths.get(System.getProperty("user.dir"), "cert.pem");

	@Test
	@DisplayName("should equals result text")
	public void matchesTest() throws Exception {
		var target = "INSERT_YOUR_TEST_TARGET_TEXT";
		var privateKey = getPrivateKey();
		var publicKey = getPublicKey();
		var encrypted = CryptoUtils.encrypt(privateKey, target);
		var decrypted = CryptoUtils.decrypt(publicKey, encrypted);
		Assertions.assertEquals(decrypted, target);
	}

	private RSAPrivateKey getPrivateKey() throws Exception {
		var keystoreInputStream = Files.newInputStream(KEYSTORE_FILE_PATH);
		KeyStore keystore = KeyStore.getInstance("jks");
		keystore.load(keystoreInputStream, KEYSTORE_PASS.toCharArray());
		return (RSAPrivateCrtKey) keystore.getKey(ALIAS, KEYSTORE_PASS.toCharArray());
	}

	private RSAPublicKey getPublicKey() throws Exception {
		var certInputStream = Files.newInputStream(PUBLIC_KEY_FILE_PATH);
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		Certificate certificate = certFactory.generateCertificate(certInputStream);
		return (RSAPublicKey) certificate.getPublicKey();
	}

}
