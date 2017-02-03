package org.utils.gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

class SimpleMapDeserializer implements JsonDeserializer<Map<String, String>> {
	public Map<String, String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Map map = new HashMap();
		if (json.isJsonObject()) {
			JsonObject jsonObject = json.getAsJsonObject();
			Iterator iterator = jsonObject.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String key = (String) entry.getKey();
				JsonElement element = (JsonElement) entry.getValue();
				String value = null;
				if (element.isJsonPrimitive()) {
					JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
					value = jsonPrimitive.getAsString();
				} else {
					value = element.toString();
				}
				map.put(key, value);
			}
		}
		return map;
	}
}
