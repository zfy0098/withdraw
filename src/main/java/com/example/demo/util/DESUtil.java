package com.example.demo.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.sun.jersey.core.util.Base64;



public class DESUtil {

	/** 编码字符集 **/
	private static String CHAR_ENCODING = "UTF-8";

	/**
	 * Base64编码
	 * @param key
	 * @param data
	 * @return
	 */
	public static String encode(String key, String data) {
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] dataByte = data.getBytes(CHAR_ENCODING);
			byte[] valueByte = des3Encryption(keyByte, dataByte);
			String value = new String(Base64.encode(valueByte), CHAR_ENCODING);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * des3Encryption加密
	 * @param key
	 * @param data
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IllegalStateException
	 */
	public static byte[] des3Encryption(byte[] key, byte[] data) throws
			NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException, IllegalStateException {
		final String Algorithm = "DESede";

		SecretKey deskey = new SecretKeySpec(key, Algorithm);

		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(data);
	}

}