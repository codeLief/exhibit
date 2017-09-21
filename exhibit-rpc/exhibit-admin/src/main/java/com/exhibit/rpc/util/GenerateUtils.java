package com.exhibit.rpc.util;

import java.util.UUID;

/**
 * @author: ankang
 */
public class GenerateUtils {
	
	public static String generateUUID(){
		
		String uuid = UUID.randomUUID().toString();
		
		return uuid.replaceAll("-", "");
	}
}
