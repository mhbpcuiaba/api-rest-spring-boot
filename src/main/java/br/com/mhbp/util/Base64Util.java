package br.com.mhbp.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public abstract class Base64Util {

	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	
	public static String decodeString (String encodedString) {
		byte[] bytesDecoded = Base64.getDecoder().decode(encodedString);
		return new String(bytesDecoded, UTF_8);
	}
	
	public static String encodeString (String normalString) {
//		normalString
		String encodeToString = Base64.getEncoder().encodeToString(normalString.getBytes());
//		byte[] bytesDecoded = Base64.getDecoder().decode(normalString);
		return encodeToString;
	}
}
