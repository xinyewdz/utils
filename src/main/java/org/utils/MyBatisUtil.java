package org.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class MyBatisUtil {
	private static Logger log = Logger.getLogger(MyBatisUtil.class);

	private static Map<DB, SqlSessionFactory> sqlSessionFactoryMap = new HashMap();

	static {
		File configFile = FileUtil.getSystemFile("classpath://mybatis-config.xml");
		if (configFile.exists()) {
			try {
				InputStream inputStream = new FileInputStream(configFile);
				SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
				SqlSessionFactory tccSqlSessionFactory = builder.build(inputStream, "tcc");
				sqlSessionFactoryMap.put(DB.tcc, tccSqlSessionFactory);
				inputStream.close();
			} catch (IOException e) {
				log.error(e);
			}

		}
	}

	public static SqlSession getSession() {
		return ((SqlSessionFactory) sqlSessionFactoryMap.get(DB.tcc)).openSession(true);
	}

	public static SqlSession getOdsSession() {
		return ((SqlSessionFactory) sqlSessionFactoryMap.get(DB.ods)).openSession(true);
	}

	public static SqlSession getOdsSession(boolean autoCommit) {
		return ((SqlSessionFactory) sqlSessionFactoryMap.get(DB.ods)).openSession(autoCommit);
	}

	public static SqlSession getOdsSession(ExecutorType type, boolean autoCommit) {
		return ((SqlSessionFactory) sqlSessionFactoryMap.get(DB.ods)).openSession(type, autoCommit);
	}

	public static String getMappeSQL(String id, Object parameter, SqlSession session) {
		MappedStatement statement = session.getConfiguration().getMappedStatement(id);
		BoundSql boundSql = statement.getBoundSql(parameter);
		return boundSql.getSql();
	}

	public static void main(String[] args) {
		getOdsSession();
	}
}
