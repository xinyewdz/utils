package org.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * TODO
 * 
 * @author Aaron
 * @date 2015年8月26日
 * 
 */
public class StringUtil {

	private static final char[] SRC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	public static String getNonceStr(int len) {
		Random random = new Random();
		char[] result = new char[len];
		int maxIdx = SRC.length - 1;
		for (int i = 0; i < len; i++) {
			int idx = random.nextInt(maxIdx);
			result[i] = SRC[idx];
		}
		return new String(result);
	}

	public static int getInt(String num, int defaultVal) {
		int result = defaultVal;
		if (!NoneUtil.isBlank(num)) {
			result = Integer.parseInt(num.trim());
		}
		return result;
	}

	public static String getString(String str, String defaultVal) {
		return NoneUtil.isBlank(str) ? defaultVal : str;
	}

	public static String collectionJoin(Collection<String> collection, String join) {
		if (NoneUtil.isBlank(join)) {
			join = "";
		}
		StringBuilder strBuilder = new StringBuilder();
		if (!NoneUtil.isEmpty(collection)) {
			Iterator<String> iterator = collection.iterator();
			boolean firstFlag = true;
			while (iterator.hasNext()) {
				String str = iterator.next();
				if (!firstFlag) {
					strBuilder.append(join);
				}
				strBuilder.append(str);
				firstFlag = false;
			}
		}
		return strBuilder.toString();
	}

	public static boolean match(String source, String... target) {
		boolean flag = false;
		if (source == null || target == null || target.length == 0) {
			return flag;
		}
		source = source.trim();
		for (int i = 0, len = target.length; i < len; i++) {
			if (source.equals(target[i].trim())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		System.out.println(getNonceStr(64));
	}

}
