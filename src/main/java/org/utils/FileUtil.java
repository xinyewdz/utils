package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {
	public static boolean copyFile(File source, File dest) throws IOException {
		if ((!source.exists()) || (source.isDirectory())) {
			return false;
		}
		FileInputStream input = new FileInputStream(source);
		return copyFile(input, dest);
	}

	public static boolean copyFile(InputStream input, File dest) throws IOException {
		if (input == null) {
			return false;
		}
		if (!dest.exists()) {
			File parent = dest.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			dest.createNewFile();
		}
		FileOutputStream output = new FileOutputStream(dest);
		try {
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = input.read(buff)) > 0) {
				output.write(buff, 0, len);
				output.flush();
			}
		} finally {
			output.flush();
			output.close();
			input.close();
		}
		return true;
	}

	public static File getSystemFile(String path) {
		if (path == null) {
			return null;
		}
		File file = null;
		if (path.startsWith("classpath://")) {
			String _path = path.substring(path.indexOf("classpath://") + 12);
			URL url = Thread.currentThread().getContextClassLoader().getResource(_path);
			if (url != null) {
				String realPath = url.getPath();
				file = new File(realPath);
			}
		} else {
			file = new File(path);
		}
		return file;
	}

	public static boolean createFile(File file) throws IOException {
		if (file == null) {
			return false;
		}
		if (file.exists()) {
			return true;
		}
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		return file.createNewFile();
	}

	public static void testIO() throws IOException {
		long start = System.currentTimeMillis();
		File file = new File("F:/XinwoEngineerMode.apk");
		FileInputStream inputStream = new FileInputStream(file);
		FileOutputStream outputStream = new FileOutputStream("F:/XinwoEngineerMode.apk.bak");
		byte[] buffer = new byte[1024 * 1024];
		int r = 0;
		while ((r = inputStream.read(buffer)) > -1) {
			outputStream.write(buffer, 0, r);
			outputStream.flush();
		}
		inputStream.close();
		outputStream.close();
		System.out.println((System.currentTimeMillis() - start));
	}

	public static void testNIO() throws IOException {
		long start = System.currentTimeMillis();
		File file = new File("F:/XinwoEngineerMode.apk");
		FileInputStream inputStream = new FileInputStream(file);
		FileChannel inChannel = inputStream.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
		FileOutputStream outputStream = new FileOutputStream("F:/XinwoEngineerMode.apk.bak");
		FileChannel outChannel = outputStream.getChannel();
		while (true) {
			buffer.clear();
			int r = inChannel.read(buffer);
			if (r == -1) {
				break;
			}
			buffer.flip();
			outChannel.write(buffer);
		}
		inputStream.close();
		outputStream.close();
		System.out.println((System.currentTimeMillis() - start));
	}

	public static void main(String[] args) throws IOException {
		testNIO();
		// testIO();
	}
}
