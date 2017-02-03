package org.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

/**
 * TODO
 * 
 * @author Aaron
 * @date 2015年7月30日
 * 
 */
public class LogUtil {

	public static final String DEFAULT_LOGGER_NAME = "XW";

	private static Logger defaultLogger = null;
	private static Logger rootLogger = null;

	static {
		boolean config = false;
		if (!config) {
			InputStream in = LogUtil.class.getClassLoader().getResourceAsStream("log4j.properties");
			if (in != null) {
				Properties prop = new Properties();
				try {
					prop.load(in);
					System.out.println("use log4j config.path=classpath://log4j.properties");
					PropertyConfigurator.configure(prop);
					config = true;
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (!config) {
			System.err.println("not find log4j.properties,use default config.");
			Logger rootLogger = Logger.getRootLogger();
			rootLogger.setLevel(Level.INFO);
			rootLogger.setAdditivity(false);
			PatternLayout layout = new PatternLayout();
			layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %C- %m%n");
			ConsoleAppender appender = new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT);
			appender.setLayout(layout);
			appender.setEncoding("UTF-8");
			rootLogger.addAppender(appender);
		}
		rootLogger = LogManager.getRootLogger();
		defaultLogger = LogManager.getLogger(DEFAULT_LOGGER_NAME);
		if (defaultLogger == null) {
			defaultLogger = rootLogger;
		}
	}

	public static Logger getRootLogger() {
		return rootLogger;
	}

	public static Logger getLogger() {
		return defaultLogger;
	}

	public static Logger getLogger(String loggerName) {
		if (NoneUtil.isBlank(loggerName)) {
			return defaultLogger;
		} else {
			return LogManager.getLogger(loggerName);
		}
	}

	public static Logger getLogger(Class clazz) {
		if (clazz == null) {
			return defaultLogger;
		} else {
			return LogManager.getLogger(clazz);
		}
	}

	public static void main(String[] args) {
		System.out.println(System.getProperties());
	}

}
