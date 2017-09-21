package com.exhibit.rpc.model;

import lombok.Data;

@Data
public class ProviderVo extends Provider {

	private static final long serialVersionUID = 1L;
	
	private int consumerCount;
	 
	private int providerCount;
}
