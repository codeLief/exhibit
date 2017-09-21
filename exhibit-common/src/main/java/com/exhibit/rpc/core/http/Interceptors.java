package com.exhibit.rpc.core.http;

import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class Interceptors {

    public static void gzip(DefaultHttpClient client, boolean open) {
      client.removeRequestInterceptorByClass(DeflateInterceptor.class);
      client.removeResponseInterceptorByClass(DeflateInterceptor.class);
      if (open) {
        client.addRequestInterceptor(DeflateInterceptor.instance);
        client.addResponseInterceptor(DeflateInterceptor.instance);
      }
    }
}
