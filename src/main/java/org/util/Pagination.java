package org.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * TODO
 * 
 * @author Aaron
 * @date 2016年7月14日
 */
public class Pagination implements Serializable {

	public static final String SORT_ORDER_ASC = "asc";
	public static final String SORT_ORDER_DESC = "desc";

	// 数据库下标从0开始，web从1开始
	private int rowStart = -1;
	private int pageSize = 10;
	private int rowTotal = 0;
	private String sortName = null;

	private String sortOrder = SORT_ORDER_ASC;
	private String sortSql = null;

	public Pagination() {
	}

	public Pagination(int rowStart, int pageSize) {
		this.rowStart = rowStart;
		this.pageSize = pageSize;
	}

	public int getRowStart() {
		return rowStart;
	}

	public Pagination setRowStart(int rowStart) {
		this.rowStart = rowStart;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Pagination setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public int getRowTotal() {
		return rowTotal;
	}

	public Pagination setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
		return this;
	}

	public String getSortName() {
		return sortName;
	}

	public Pagination setSortName(String sortName) {
		this.sortName = sortName;
		return this;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public Pagination setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
		return this;
	}

	public String getSortSql() {
		return sortSql;
	}

	public Pagination setSortSql(String sortSql) {
		this.sortSql = sortSql;
		return this;
	}

	public String print() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_rowStart", this.rowStart);
		map.put("_pageSize", this.pageSize);
		map.put("_rowTotal", this.rowTotal);
		map.put("_sortName", this.sortName);
		map.put("_sortOrder", this.sortOrder);
		Gson gson = new Gson();
		return gson.toJson(map);
	}

	@Override
	public String toString() {
		return print();
	}

}
