package com.exhibit.rpc.core.http;

import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class Strategies {
    
	public static void keepAlive(DefaultHttpClient client, long time){
      client.setKeepAliveStrategy(new ForceKeepAliveStrategy(time));
    }
}
