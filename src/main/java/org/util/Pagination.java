package org.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
	private int startRow = -1;
	private int pageSize = 10;
	private long totalRow = 0;
	private int currentPage = 1;
	private int totalPage = 0;
	private String sortName = null;

	private String sortOrder = SORT_ORDER_ASC;
	private String sortSql = null;

	public Pagination() {
	}

	public Pagination(int startRow, int pageSize) {
		this.startRow = startRow;
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public Pagination setRowStart(int startRow) {
		this.startRow = startRow;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Pagination setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public long getTotalRow() {
		return totalRow;
	}

	public Pagination setTotalRow(long totalRow) {
		this.totalRow = totalRow;
		this.totalPage = (int) (totalRow/pageSize);
		if(totalRow%pageSize>0){
			totalPage++;
		}
		this.currentPage = startRow/pageSize;
		this.currentPage++;
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
	
	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public String print() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_startRow", this.startRow);
		map.put("_pageSize", this.pageSize);
		map.put("_totalRow", this.totalRow);
		map.put("_sortName", this.sortName);
		map.put("_sortOrder", this.sortOrder);
		return map.toString();
	}

	@Override
	public String toString() {
		return print();
	}

}
