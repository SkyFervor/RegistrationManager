package com.skyfervor.framework.vobase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageVo<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认分页
	 */
	public static final long DEFAULT_PAGE_SIZE = 20;

	/**
	 * 本页容量
	 */
	private long pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 起始位置
	 */
	private long start;

	/**
	 * 数据List
	 */
	private List<T> rows;

	/**
	 * 总记录数
	 */
	private long records;

	/**
	 * 
	 */
	public PageVo() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 
	 * @param start
	 *            起始位置
	 * @param records
	 *            总记录数
	 * @param pageSize
	 *            本页容量
	 * @param rows
	 *            本页条数
	 */
	public PageVo(long start, long records, long pageSize, List<T> rows) {
		this.start = start;
		this.records = records;
		this.pageSize = pageSize;
		this.rows = rows;
	}

	/**
	 * 
	 * @param records
	 *            总记录数
	 * @param rows
	 *            本页条数
	 */
	public PageVo(long records, List<T> rows) {
		this.records = records;
		this.rows = rows;
	}

	/**
	 * 起始位置
	 */
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * 总记录数
	 */
	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	/**
	 * 本页容量
	 */
	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 数据List
	 */
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * @return 总页数
	 */
	public long getTotal() {
		if ((records % pageSize) == 0) {
			return records / pageSize;
		} else {
			return (records / pageSize) + 1;
		}
	}

	/**
	 * @return 当前页数
	 */
	public long getPage() {
		return (start / pageSize) + 1;
	}

	/**
	 * @return 是否有后页
	 */
	public boolean hasNextPage() {
		return this.getPage() < this.getTotal();
	}

	/**
	 * @return 是否有前页
	 */
	public boolean hasPreviousPage() {
		return this.getPage() > 1;
	}

	/**
	 * @return 起始位置(默认容量)
	 */
	protected static long getStartOfPage(long pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * @return 起始位置
	 */
	public static long getStartOfPage(long pageNo, long pageSize) {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * @return 最后一条起始位置(默认容量)
	 */
	public long getEndOfPage(long pageNo, long pageSize) {
		if (this.getRecords() == 0)
			return 0;
		if (pageNo == this.getTotal()) {
			return (long) this.getRecords() - 1;
		} else {
			return getStartOfPage(pageNo + 1, pageSize) - 1;
		}
	}
}
