package org.utils.gson;

import java.util.Map;

import org.utils.NoneUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	public static final String DEFAULT_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String toJson(Object obj) {
		if (obj == null) {
			return null;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		String content = gson.toJson(obj);
		return content;
	}

	public static <T> T fromJson(String content, Class<T> clazz) {
		if (NoneUtil.isBlank(content)) {
			return null;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		T t = gson.fromJson(content, clazz);
		return t;
	}

	public static <T> T fromJson(String content, TypeToken<T> typeToken) {
		if (NoneUtil.isBlank(content)) {
			return null;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		T t = gson.fromJson(content, typeToken.getType());
		return t;
	}

	public static Map<String, String> fromJsonAsSimpleMap(String content) {
		if (NoneUtil.isBlank(content)) {
			return null;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()
				.registerTypeAdapter(Map.class, new SimpleMapDeserializer()).create();
		Map<String, String> map = gson.fromJson(content, new TypeToken<Map<String, String>>() {
		}.getType());
		return map;
	}

	public static Map<String, Object> fromJsonAsMap(String content) {
		if (NoneUtil.isBlank(content)) {
			return null;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()
				.registerTypeAdapter(Map.class, new MapDeserializer()).create();
		Map<String, Object> map = gson.fromJson(content, new TypeToken<Map<String, Object>>() {
		}.getType());
		return map;
	}

	public static void main(String[] args) {
		String content = "{\"timestamp\":1473310683,\"errno\":\"0\"}";
		fromJsonAsSimpleMap(content);
	}
}
