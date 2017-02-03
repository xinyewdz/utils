package org.utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class NoneUtil {
	public static Date getDate(Date date) {
		if (date == null) {
			date = new Date(0L);
		}
		return date;
	}

	public static boolean isBlank(String str) {
		return (str == null) || (str.trim().equals(""));
	}

	public static String getValue(String val, String defaultVal) {
		return (isBlank(val)) ? defaultVal : val;
	}

	public static boolean isEmpty(Collection collection) {
		return (collection == null) || (collection.isEmpty());
	}

	public static boolean isEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat();

		System.out.println(sdf.format(getDate(null)));
	}
}
