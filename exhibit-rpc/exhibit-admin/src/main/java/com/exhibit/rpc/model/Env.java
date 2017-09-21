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
public class Env extends BaseModel {

	private static final long serialVersionUID = 5409185459234711691L;

	/**
	 * 
	 */
	private long envId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String host;
	/**
	 * 
	 */
	private int port;
}