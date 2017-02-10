package org.util.gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

class MapDeserializer implements JsonDeserializer<Map<String, Object>> {
	public Map<String, Object> deserialize(JsonElement rootEle, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		Map obj = null;
		if (rootEle.isJsonObject()) {
			JsonElement element = rootEle.getAsJsonObject();
			obj = processObject(element);
		}
		return obj;
	}

	private Map<String, Object> processObject(JsonElement rootEle) {
		Map obj = new HashMap();
		JsonObject jsonObject = rootEle.getAsJsonObject();
		Iterator iterator = jsonObject.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			Object val = null;
			JsonElement element = (JsonElement) entry.getValue();
			if (element.isJsonArray())
				val = processArray(element);
			else if (element.isJsonObject())
				val = processObject(element);
			else if (element.isJsonPrimitive()) {
				val = element.getAsString();
			}
			obj.put(key, val);
		}
		return obj;
	}

	private Object[] processArray(JsonElement rootEle) {
		JsonArray jsonArray = rootEle.getAsJsonArray();
		int size = jsonArray.size();
		Object[] objs = new Object[size];
		for (int i = 0; i < size; ++i) {
			JsonElement element = jsonArray.get(i);
			if (element.isJsonArray())
				objs[i] = processArray(element);
			else if (element.isJsonObject())
				objs[i] = processObject(element);
			else if (element.isJsonPrimitive()) {
				objs[i] = element.getAsString();
			}
		}
		return objs;
	}
}
