package org.util;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * 
 * @author Aaron
 * @date 2016年8月10日
 */
public class APIResponseUtil {

	public static void put(ApiResponse<Map<String, Object>> apiResp, String key, Object val) {
		Map<String, Object> data = apiResp.getData();
		if (data == null) {
			data = new HashMap<String, Object>();
			apiResp.success(data);
		}
		data.put(key, val);
	}

}
