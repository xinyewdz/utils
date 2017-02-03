package org.utils;

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

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortSql() {
		return sortSql;
	}

	public void setSortSql(String sortSql) {
		this.sortSql = sortSql;
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
