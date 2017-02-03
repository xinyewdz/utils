package org.utils;

public enum DB {
	tcc("tcc"), ods("ods");

	public static final String DBNAME_TCC = "tcc";
	public static final String DBNAME_ODS = "ods";
	private String dbName = null;

	private DB(String dbName) {
		this.dbName = dbName;
	}

	public String getDBName() {
		return this.dbName;
	}

	public static DB getDB(String dbName) {
		DB db = null;
		if ("tcc".equals(dbName))
			db = tcc;
		else if ("ods".equals(dbName)) {
			db = ods;
		}
		return db;
	}
}
