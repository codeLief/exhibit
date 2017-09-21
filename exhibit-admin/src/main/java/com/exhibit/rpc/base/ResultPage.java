package com.exhibit.rpc.base;

import lombok.Data;

import java.util.List;

@Data
public final class ResultPage {

	// 当前页
	private int currentPage = 1;
	// 每页显示数量
	private int pageSize = 10;
	// 总页数
	private int pageCount = 1;
	// 总条数
	private int totalCount;

	// 存放查询结果用的list
	private List items;

	public ResultPage(int totalCount, int pageSize, int currentPage, List items) {
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.pageCount = operatorPageCount();
		this.items = items;
	}

	/**
	 * 计算总页数
	 *
	 * @return
	 */
	public int operatorPageCount() {
		int pageCount = 0;
		if (pageSize != 0) {
			pageCount = totalCount / pageSize;
			if (totalCount % pageSize != 0)
				pageCount++;
		}

		return pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}
}