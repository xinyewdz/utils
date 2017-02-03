package org.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * TODO
 * 
 * @author Aaron
 * @date 2016年9月5日
 */
public class SerializeUtil {

	public static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = null;
		byte[] result = null;
		try {
			outputStream = new ObjectOutputStream(byteOutputStream);
			outputStream.writeObject(object);
			result = byteOutputStream.toByteArray();
		} catch (IOException e) {

		} finally {
			try {
				if (byteOutputStream != null) {
					byteOutputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
			}
		}
		return result;
	}

	public static Object unSerialize(byte[] src) {
		if (src == null || src.length == 0) {
			return null;
		}
		ByteArrayInputStream byteInput = new ByteArrayInputStream(src);
		ObjectInputStream inputStream = null;
		Object object = null;
		try {
			inputStream = new ObjectInputStream(byteInput);
			object = inputStream.readObject();
		} catch (Exception e) {

		} finally {
			try {
				if (byteInput != null) {
					byteInput.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
			}
		}

		return object;
	}

}
