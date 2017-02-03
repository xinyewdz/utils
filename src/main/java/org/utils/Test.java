package org.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) throws Exception {
		// test1();
		// test2();
		test3();
	}

	private static void test3() {
		long start = System.currentTimeMillis();
		List<Integer> l1 = new ArrayList<Integer>();
		for (int i = 0; i < 10000000; i++) {
			l1.add(i);
		}
		System.out.println((System.currentTimeMillis() - start));
		long start1 = System.currentTimeMillis();
		List<Integer> l2 = new ArrayList<Integer>();
		for (int i = 10000000; i < 20000000; i++) {
			l2.add(i);
		}
		System.out.println((System.currentTimeMillis() - start1));
		long start2 = System.currentTimeMillis();
		List<Integer> l3 = new ArrayList<Integer>();
		for (int i = 20000000; i < 30000000; i++) {
			l3.add(i);
		}
		System.out.println((System.currentTimeMillis() - start2));
	}

	private static void test2() throws Exception {
		File f = new File("d:/a.txt");
		FileOutputStream fos = new FileOutputStream(f);
		fos.write("aa".getBytes());
		fos.flush();

	}

	public static void test1() {
		String string = "0: 463	459	483";
		Pattern pattern = Pattern.compile("[raw|\\d+]: (\\d+)\t(\\d+)\t(\\d+)");
		Matcher matcher = pattern.matcher(string);
		if (matcher.find()) {
			System.out.println(matcher.group(1) + "," + matcher.group(2) + "," + matcher.group(3));
		}
	}
}
