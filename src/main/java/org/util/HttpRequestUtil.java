package org.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * TODO
 *
 * @author Aaron
 * @date 2016年7月14日
 */
public class HttpRequestUtil {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static Integer getInteger(HttpServletRequest request, String name) throws RequestParameterException {
		String val = request.getParameter(name);
		Integer result = null;
		try {
			if (!NoneUtil.isBlank(val)) {
				result = Integer.valueOf(val);
			}
		} catch (NumberFormatException e) {
			throw new RequestParameterException(e);
		}
		return result;
	}

	public static int getInt(HttpServletRequest request, String name, int defaultVal) throws RequestParameterException {
		String val = request.getParameter(name);
		int result = 0;
		try {
			if (!NoneUtil.isBlank(val)) {
				result = Integer.valueOf(val);
			} else {
				result = defaultVal;
			}
		} catch (NumberFormatException e) {
			throw new RequestParameterException(e);
		}
		return result;
	}

	public static int getInt(HttpServletRequest request, String name) throws RequestParameterException {
		return getInt(request, name, 0);
	}

	public static Long getLong(HttpServletRequest request, String name) throws RequestParameterException {
		String val = request.getParameter(name);
		Long result = null;
		try {
			if (!NoneUtil.isBlank(val)) {
				result = Long.valueOf(val);
			}
		} catch (NumberFormatException e) {
			throw new RequestParameterException(e);
		}
		return result;
	}

	public static Byte getByte(HttpServletRequest request, String name) throws RequestParameterException {
		String val = request.getParameter(name);
		Byte result = null;
		try {
			if (!NoneUtil.isBlank(val)) {
				result = Byte.valueOf(val);
			}
		} catch (NumberFormatException e) {
			throw new RequestParameterException(e);
		}
		return result;
	}

	public static float getFloat(HttpServletRequest request, String name) throws RequestParameterException {
		String val = request.getParameter(name);
		float result = 0;
		try {
			if (!NoneUtil.isBlank(val)) {
				result = Float.parseFloat(val);
			}
		} catch (NumberFormatException e) {
			throw new RequestParameterException(e);
		}
		return result;
	}

	public static boolean getBool(HttpServletRequest request, String name) {
		String val = request.getParameter(name);
		return "Y".equals(val);
	}

	public static String getString(HttpServletRequest request, String name, String defaultVal) {
		String val = request.getParameter(name);
		if (val == null) {
			val = defaultVal;
		}
		return val;
	}

	public static String getString(HttpServletRequest request, String name) {
		return getString(request, name, null);
	}

	public static boolean valideNull(Object... objs) {
		boolean flag = false;
		for (int i = 0, len = objs.length; i < len; i++) {
			Object object = objs[i];
			if (object == null) {
				flag = true;
				break;
			}
			if (object instanceof String) {
				String str = (String) object;
				if (NoneUtil.isBlank(str)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public static Date getDate(HttpServletRequest request, String name, String pattern)
			throws RequestParameterException {
		String val = request.getParameter(name);
		Date date = null;
		try {
			if (!NoneUtil.isBlank(val)) {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				date = sdf.parse(val);
			}
		} catch (Exception e) {
			throw new RequestParameterException(e);
		}
		return date;
	}

	public static Date getDate(HttpServletRequest request, String name) throws RequestParameterException {
		return getDate(request, name, DEFAULT_DATE_PATTERN);
	}

	public static <T> T getSimpleObj(HttpServletRequest request, Class<T> clazz) throws RequestParameterException {
		Enumeration<String> enumera = request.getParameterNames();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		while (enumera.hasMoreElements()) {
			String key = enumera.nextElement();
			String[] vals = request.getParameterValues(key);
			if (vals.length == 1) {
				String val = vals[0];
				if (NoneUtil.isBlank(val)) {
					continue;
				}
				paraMap.put(key, vals[0]);
			} else {
				paraMap.put(key, vals);
			}
		}
		T t = null;
		try {
			Gson gson = new GsonBuilder().setDateFormat(DEFAULT_DATE_PATTERN).serializeNulls().create();
			String content = gson.toJson(paraMap);
			t = gson.fromJson(content, clazz);
		} catch (JsonSyntaxException e) {
			throw new RequestParameterException(e);
		}
		return t;
	}

	public static String getBody(HttpServletRequest request) throws RequestParameterException {

		int len = request.getContentLength();
		String body = null;
		try {
			InputStream inputStream = request.getInputStream();
			byte[] buff = new byte[len];
			inputStream.read(buff);
			body = new String(buff, "UTF-8");
		} catch (Exception e) {
			throw new RequestParameterException(e);
		}
		return body;

	}

	public static Map<String, Object> getRequesetParameters(HttpServletRequest request) {
		Enumeration<String> enumeration = request.getParameterNames();
		Map<String, Object> map = new HashMap<String, Object>();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}

	public static Pagination getPagination(HttpServletRequest request) throws RequestParameterException {
		int _rowStart = HttpRequestUtil.getInt(request, "_rowStart");
		int _pageSize = HttpRequestUtil.getInt(request, "_pageSize");
		String _sortName = HttpRequestUtil.getString(request, "_sortName");
		String sortOrder = HttpRequestUtil.getString(request, "_sortOrder");

		if (_rowStart < 1 && NoneUtil.isBlank(_sortName)) {
			return null;
		}

		Pagination pagination = new Pagination();

		if (_rowStart > 0) {
			// 前端从1开始，后台从0开始。
			pagination.setRowStart(_rowStart - 1);
			if (_pageSize > 0) {
				pagination.setPageSize(_pageSize);
			}
		}
		if (!NoneUtil.isBlank(_sortName)) {
			pagination.setSortName(_sortName);

			if (Pagination.SORT_ORDER_DESC.equalsIgnoreCase(sortOrder)) {
				pagination.setSortOrder(Pagination.SORT_ORDER_DESC);
			} else {
				pagination.setSortOrder(Pagination.SORT_ORDER_ASC);
			}
		}
		return pagination;
	}

}
