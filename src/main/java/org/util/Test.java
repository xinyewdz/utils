package org.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
	public static void main(String[] args) throws Exception {
		// test1();
		// test2();
		// test3();
		//test4();
		//test5();
		//testFile();
		threadTest();
	}
	
	private static void threadTest() throws InterruptedException {
		Thread t1 = new Thread(()->{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("thread sleep 2 second,finish");
		});
		t1.start();
		t1.wait();
		Thread t2 = new Thread(()->{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("thread sleep 3 second,finish");
		});
		t2.start();
		t2.join();
		System.out.println("run main thread...");
		
		
		
	}

	private static void testFile() throws IOException, InterruptedException {
		FileInputStream fis = new FileInputStream("f:\\os\\ubuntu-16.04-desktop-amd64.iso");
		byte[] buff = new byte[1024000];
		int len = 0;
		FileOutputStream fos = new FileOutputStream("f:\\os\\ubuntu-16.04-desktop-amd64.iso.bak");
		while((len=fis.read(buff))>0){
			fos.write(buff, 0, len);
			fos.flush();
			Thread.sleep(5000);
		}
		fis.close();
		fos.close();
		
	}

	private static void test5() {
		Stream<Long> stream = Stream.generate(new Nature());
		List<Long> list = stream.limit(10000000).collect(Collectors.toList());
		list.parallelStream().forEach(x->System.out.println(x));
		
	}

	static class Nature implements Supplier<Long>{
		
		private Long value = 0l;

		@Override
		public Long get() {
			this.value = this.value+1;
			return this.value;
		}
		
	}

	private static void test4() {
		
		new Thread(() -> System.out.println("the lambda run...")).start();
		List<String> list = Arrays.asList("aa", "bb");
		list.forEach((String str) -> {
			System.out.println(str);
		});
		filter((String str) -> {
			return (str.length() > 2);
		}, list);
		testLambda(( a,  b) -> {
			return a + b;
		});
		list.stream().filter((str)->str.length()>1);
		//list.stream().map(str->str+="..").forEach(str->System.out.println(str));
		System.out.println(list.stream().collect(Collectors.joining(",")));
		System.out.println(list.stream().map((str)->str=str+".").reduce((sum,item)->sum=sum+"-"+item).get());
		function((item)->item+"...", list);
		
		
	}

	private static void filter(Predicate<String> predicate, List<String> list) {
		list.forEach((String str) -> {
			if (predicate.test(str)) {
				System.out.println(str);
			}
		});
		//list.stream().filter(str->predicate.test(str)).forEach(str->System.out.println(str));
	}
	
	private static void function(Function<String,String> func,List<String> list){
		list.forEach((str)->{
			String result = func.apply(str);
			System.out.println(result);
		});
	}

	private static void testLambda(ILambda lambda) {
		int c = lambda.add(3, 4);
		System.out.println(c);
	}

	interface ILambda {
		int add(int a, int b);
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
