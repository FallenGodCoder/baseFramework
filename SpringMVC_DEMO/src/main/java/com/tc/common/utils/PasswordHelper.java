package com.tc.common.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  密码工具
 *
 */
public final class PasswordHelper {

	/**
	 * 
	 */
	private PasswordHelper() {

	}

	/**
	 * 
	 * @param password
	 * @return
	 */
	public static String encode(final String password) {
		byte[] digest;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			digest = md5.digest(password.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 not supported!");
		}
		return new String(Hex.encodeHex(digest));
	}

}