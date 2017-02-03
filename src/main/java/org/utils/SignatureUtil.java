package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Aaron
 * @date 2016年9月6日
 */
public class SignatureUtil {

	private static final String ALGORITHM_SHA1 = "SHA1";
	private static final char[] HexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static String signSha1(String token, Map<String, Object> paraMap) {
		ArrayList<String> signlist = new ArrayList<String>();
		signlist.add(token);
		for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
			if ("sign".equals(entry.getKey())) {
				continue;
			}
			if (entry.getValue() != null) {
				signlist.add(String.valueOf(entry.getValue()));
			}
		}

		Collections.sort(signlist);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < signlist.size(); i++) {
			builder.append(signlist.get(i));
		}

		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_SHA1);
			messageDigest.update(builder.toString().getBytes("utf-8"));
			byte[] bytes = messageDigest.digest();
			int len = bytes.length;
			StringBuilder buf = new StringBuilder(len * 2);

			for (int j = 0; j < len; j++) {
				buf.append(HexDigits[(bytes[j] >> 4) & 0x0f]);
				buf.append(HexDigits[bytes[j] & 0x0f]);
			}
			return buf.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String md5(String message) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(message.getBytes("utf-8"));
			byte[] bytes = messageDigest.digest();
			int len = bytes.length;
			StringBuilder buf = new StringBuilder(len * 2);

			for (int j = 0; j < len; j++) {
				buf.append(HexDigits[(bytes[j] >> 4) & 0x0f]);
				buf.append(HexDigits[bytes[j] & 0x0f]);
			}
			return buf.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String signFileMd5(String path) {
		String md5Str = null;
		FileInputStream in = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			md5Str = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return md5Str;
	}

	public static void main(String[] args) {
		test1();
	}

	private static void test2() {
		String md5 = signFileMd5("C:/Users/wdzaa/Desktop/xinwo/pkg/1.0.zip");
		System.out.println(md5);

	}

	private static void test1() {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("uid", 159041225);
		paraMap.put("timestamp", 1504771348);
		paraMap.put("userStatus", "1");
		paraMap.put("userPeriod", "12");
		paraMap.put("userCycle", "20");
		paraMap.put("uri", "/xinwo/user/update");
		String token = "QGWARnUTdGeYpc4NVhysIzY8ZLrtDzuafenNJ4djDLYbagptJW0tT4Xy6Dw1HagL";
		System.out.println(signSha1(token, paraMap));
	}

}
