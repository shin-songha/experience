package com.study.exprience.support;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA256 Util - 개인 정보를 암/복호화 하기 위한 모듈 - RSA256 양방향 암호화 알고리즘을 사용
 *
 * @author songha Shin
 * @since 2019-12-08.
 */
public class RSAUtil {

	/**
	 * RSA256 암/복호화를 하기위한 KeyPair를 생성 후 반환한다.
	 *
	 * @return KeyPair
	 */
	public static KeyPair generateKeyPair() {

		KeyPair keyPair = null;

		try {
			KeyPairGenerator clsKeyPairGenerator = KeyPairGenerator.getInstance("RSA");

			clsKeyPairGenerator.initialize(2048);
			keyPair = clsKeyPairGenerator.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return keyPair;
	}

	/**
	 * 공개키를 이용하여 평문 텍스트를 byte 배열로 암호화하여 16진 문자열로 반환한다.
	 *
	 * @param originValue 원본 텍스트
	 * @param publicKey   공개키
	 * @return 암호화 된 16진 String
	 */
	public static String encrypt(String originValue, PublicKey publicKey) {

		byte[] bytes = originValue.getBytes();
		String encryptedText = null;

		try {
			Cipher cipher = Cipher.getInstance("RSA");

			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedText = byteArrayToHex(cipher.doFinal(bytes));
		} catch (NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException
				| NoSuchPaddingException e) {
			e.printStackTrace();
		}

		return encryptedText;
	}

	/**
	 * 비밀키를 이용하여 암호화된 텍스트를 byte 배열로 변경 후 복호화하여 문자열로 반환한다.
	 *
	 * @param securedValue 암호화된 텍스트
	 * @param privateKey   비밀키
	 * @return 복호화 된 String
	 */
	public static String decrypt(String securedValue, PrivateKey privateKey) {

		String decryptedValue = null;

		try {
			Cipher cipher = Cipher.getInstance("RSA");
			byte[] encryptedBytes = hexToByteArray(securedValue);

			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			decryptedValue = new String(decryptedBytes, "utf-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException
				| BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decryptedValue;
	}

	/**
	 * 16진 문자열을 byte 배열로 변환한다.
	 *
	 * @param hex 16진 문자열
	 * @return byte[]
	 */
	private static byte[] hexToByteArray(String hex) {

		if (hex == null || hex.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] bytes = new byte[hex.length() / 2];

		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}

		return bytes;
	}

	/**
	 * byte 배열을 16진 문자열로 변환한다.
	 *
	 * @param bytes byte 배열
	 * @return String
	 */
	private static String byteArrayToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();

		for (final byte b : bytes)
			sb.append(String.format("%02x", b & 0xff));

		return sb.toString();
	}

	/**
	 * 공개키로부터 RSAPublicKeySpec 객체를 생성한다.
	 *
	 * @param publicKey 공개키
	 * @return RSAPublicKeySpec spec
	 */
	public static RSAPublicKeySpec getRSAPublicKeySpec(PublicKey publicKey) {

		RSAPublicKeySpec spec = null;

		try {
			spec = KeyFactory.getInstance("RSA").getKeySpec(publicKey, RSAPublicKeySpec.class);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return spec;
	}

	/**
	 * 비밀키로부터 RSAPrivateKeySpec 객체를 생성한다.
	 *
	 * @param privateKey 비밀키
	 * @return RSAPrivateKeySpec
	 */
	public static RSAPrivateKeySpec getRSAPrivateKeySpec(PrivateKey privateKey) {

		RSAPrivateKeySpec spec = null;

		try {
			spec = KeyFactory.getInstance("RSA").getKeySpec(privateKey, RSAPrivateKeySpec.class);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return spec;
	}

	/**
	 * keyPair를 이용하여 공개키를 생성한다.
	 *
	 * @param keyPair 공개키를 생성하기 위한 KeyPair
	 * @return PublicKey 공개키
	 */
	public static PublicKey getPublicKey(KeyPair keyPair) {

		PublicKey publicKey = null;

		try {
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return publicKey;
	}

	/**
	 * keyPair를 이용하여 비밀키를 생성한다.
	 *
	 * @param keyPair 비밀키를 생성하기 위한 KeyPair
	 * @return PrivateKey 비밀키
	 */
	public static PrivateKey getPrivateKey(KeyPair keyPair) {

		PrivateKey privateKey = null;

		try {
			privateKey = keyPair.getPrivate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return privateKey;
	}

	/**
	 * 문자열로 Encoding 된 키 이용하여 공개키를 생성한다.
	 *
	 * @param encodedKey 문자열로 Encoding 된 키
	 * @return PublicKey 공개키
	 */
	public static PublicKey generatePublicKey(String encodedKey) {

		PublicKey publicKey = null;

		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(encodedKey.getBytes()));

			publicKey = keyFactory.generatePublic(publicKeySpec);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}

		return publicKey;
	}

	/**
	 * 문자열로 Encoding 된 키 이용하여 비밀키를 생성한다.
	 *
	 * @param encodedKey 문자열로 Encoding 된 키
	 * @return PrivateKey 비밀키
	 */
	public static PrivateKey generatePrivateKey(String encodedKey) {

		PrivateKey privateKey = null;

		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(encodedKey.getBytes()));

			privateKey = keyFactory.generatePrivate(privateKeySpec);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}

		return privateKey;
	}

//    public static String getPublicKey() {
//        final KeyPair keyPair = RSAUtil.generateKeyPair();
//        final RSAPublicKeySpec rsaPublicKeySpec = RSAUtil.getRSAPublicKeySpec(RSAUtil.getPublicKey(keyPair));
//        String sPrivateKey = Base64.encodeBase64String(RSAUtil.getPrivateKey(keyPair).getEncoded());
//        String sPublicKey = Base64.encodeBase64String(RSAUtil.getPublicKey(keyPair).getEncoded());
//
//        RSAKeyManager rsaKeyManager = new RSAKeyManager();
//
//        rsaKeyManager.saveKeyPair(sPrivateKey, sPublicKey);
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("modulus", rsaPublicKeySpec.getModulus().toString(16));
//        map.put("sPublicKey", sPublicKey);
//        map.put("exponent", rsaPublicKeySpec.getPublicExponent().toString(16));
//
//        return sPublicKey;
//    }
}
