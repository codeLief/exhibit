package com.exhibit.rpc.core.http;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

public class ForceKeepAliveStrategy extends DefaultConnectionKeepAliveStrategy{
    
      private final long time;

      public ForceKeepAliveStrategy(long time) {
        this.time = time;
      }

      public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
        long keepAlive = super.getKeepAliveDuration(response, context);
        if (keepAlive == -1L) {
          keepAlive = this.time;
        }
        return keepAlive;
      }

}
