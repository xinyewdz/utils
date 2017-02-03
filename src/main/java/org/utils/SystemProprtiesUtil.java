package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class SystemProprtiesUtil {
	private static Properties sysProp = new Properties();

	private static String basePath = "";

	static {
		basePath = System.getProperty("basePath");
		if (NoneUtil.isBlank(basePath)) {
			basePath = "classpath://";
		}
		System.err.println("the basePath=" + basePath);
		sysProp.setProperty("basePath", basePath);
		File systemFile = FileUtil.getSystemFile(basePath + "/system.properties");
		if (systemFile != null && systemFile.exists()) {
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(systemFile);
				Properties _prop = new Properties();
				_prop.load(inputStream);
				Iterator<Map.Entry<Object, Object>> iterator = _prop.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Object, Object> entry = iterator.next();
					String value = (String) entry.getValue();
					String key = (String) entry.getKey();
					String newValue = simpleTemplate(value, sysProp);
					sysProp.put(key, newValue);
				}
				Logger log = LogUtil.getLogger();
				log.info("the sysProp=" + sysProp);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static String getProperty(String key) {
		return sysProp.getProperty(key);
	}

	public static String simpleTemplate(String templateStr, Properties data) {
		if (templateStr == null)
			return null;

		if (data == null) {
			data = new Properties();
		}

		Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");

		StringBuffer newValue = new StringBuffer(templateStr.length());

		Matcher matcher = pattern.matcher(templateStr);

		while (matcher.find()) {
			String key = matcher.group(1);
			String r = data.get(key) != null ? data.get(key).toString() : ".";
			matcher.appendReplacement(newValue, r.replaceAll("\\\\", "\\\\\\\\")); // 这个是为了替换windows下的文件目录在java里用\\表示
		}

		matcher.appendTail(newValue);

		return newValue.toString();
	}

	public static void main(String[] args) {
		String templte = "bb  ${aa} aa";
		Properties data = new Properties();
		data.put("aa", "AA");
		System.out.println(simpleTemplate(templte, data));
	}

}
