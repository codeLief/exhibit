package com.exhibit.rpc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.exhibit.rpc.base.BaseModel;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/05/18 17:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Heartbeat extends BaseModel {

	private static final long serialVersionUID = 5409185459234711691L;

	/**
	 * 
	 */
	private String heartbeatUrl;
	/**
	 * 
	 */
	private int heartbeatTime;
	
	private int env;
}