package com.exhibit.rpc.model;


import com.exhibit.rpc.base.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/04/25 14:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Provider extends BaseModel {

	private static final long serialVersionUID = 5409185459234711691L;

	/**
	 * 服务地址（相对）
	 */
	private String sUrl;
	/**
	 * 服务环境
	 */
	private long sEnv;
	/**
	 * 服务项目名
	 */
	private String sAppName;
	/**
	 * 服务接口
	 */
	private String sInterface;
	/**
	 * 提供服务方法
	 */
	private String sMethods;
	/**
	 * 提供人
	 */
	private String author;
}