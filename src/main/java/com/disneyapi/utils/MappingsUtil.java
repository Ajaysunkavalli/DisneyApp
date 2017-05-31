package com.disneyapi.utils;

import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

public class MappingsUtil {

	public static String getJSONString(Map<String, String> properties) {
		JSONObject js = new JSONObject();
		if (properties != null) {
			Set<String> keys = properties.keySet();
			for (String key : keys) {
				String value = properties.get(key);
				js.put(key, value);
			}
		}
		return js.toJSONString();
	}

}
