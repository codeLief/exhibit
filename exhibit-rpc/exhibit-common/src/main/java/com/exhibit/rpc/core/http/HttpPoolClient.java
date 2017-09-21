package com.exhibit.rpc.core.http;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 封装连接池之后的httpClient
 * @author woodle
 *
 */
public class HttpPoolClient extends DefaultHttpClient {
    static Logger log = Logger.getLogger(HttpPoolClient.class);

    public HttpPoolClient(ClientConnectionManager conman, HttpParams params) {
        super(conman, params);
    }

    public HttpPoolClient(HttpParams params) {
        this(null, params);
    }

    public HttpPoolClient() {
        this(null);
    }

    public static HttpPoolClient createDefaultClient() {
        HttpParams params = new BasicHttpParams();
        
        // 默认的超时时间
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 10000);

        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (Windows NT 6.1; WOW64) Chrome/27.0.1453.94 Safari/537.36 qunarhc/8.0.1");

        HttpClientParams.setCookiePolicy(params, "ignoreCookies");

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry, 1L, TimeUnit.MINUTES);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(50);

        HttpPoolClient client = new HttpPoolClient(cm, params);

        Interceptors.gzip(client, true);
        Strategies.keepAlive(client, 5000L);
       
        return client;
    }

    public static void main(String[] args) throws ClientProtocolException, IOException {
        HttpPoolClient httpClient = createDefaultClient();

        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 2000);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 1000);
      
        BasicHttpRequest request = new BasicHttpRequest("GET", "/maps/api/geocode/json?address="
                + URLEncoder.encode("锦江之星沈阳南湖公园店", "UTF-8") + "&region=cn&sensor=" + false + "&language=zh-cn",
                HttpVersion.HTTP_1_1);

        request.addHeader("Host", "maps.google.com");
        HttpHost host = new HttpHost("115.28.20.72", 8010);
        HttpResponse response = httpClient.execute(host, request);

        HttpEntity entity = null;
        String content = null;
        try {
            StatusLine status = response.getStatusLine();
            entity = response.getEntity();

            if ((status != null) && (status.getStatusCode() == 200)) {
                content = EntityUtils.toString(entity, "UTF-8");
                entity = null;
            } else {
                System.out.println(status);
                System.out.println(EntityUtils.toString(entity, "UTF-8"));
            }
        } catch (Exception e) {
            log.warn("无法获取", e);
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (IOException ex) {
                log.warn("net io excepiton", ex);
            }
        }

        if (content == null) {
            return;
        }
        

    }
}
