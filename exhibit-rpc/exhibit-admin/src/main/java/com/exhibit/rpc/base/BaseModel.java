package com.exhibit.rpc.base;

import java.io.Serializable;

import lombok.Data;


@Data
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 创建时间
	 */
	private long createAt;
	/**
	 * 更新时间
	 */
	private long updateAt;
}
