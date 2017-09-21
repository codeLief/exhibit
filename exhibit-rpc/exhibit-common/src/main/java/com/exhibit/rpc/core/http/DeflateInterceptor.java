package com.exhibit.rpc.core.http;

import org.apache.http.*;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class DeflateInterceptor implements HttpRequestInterceptor, HttpResponseInterceptor {

    public static final DeflateInterceptor instance = new DeflateInterceptor();

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        request.addHeader("Accept-Encoding", "gzip,deflate");
    }

    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            Header ceheader = entity.getContentEncoding();
            if (ceheader != null) {
                HeaderElement[] codecs = ceheader.getElements();
                int leng = codecs.length;
                int i = 0;
                if (i < leng) {
                    HeaderElement codec = codecs[i];
                    if ("gzip".equalsIgnoreCase(codec.getName())) {
                        response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                        return;
                    }
                    if ("deflate".equalsIgnoreCase(codec.getName())) {
                        response.setEntity(new DeflateDecompressingEntity(response.getEntity()));
                        return;
                    }
                    if ("identity".equalsIgnoreCase(codec.getName())) {
                        return;
                    }
                    throw new HttpException("Unsupported Content-Coding: " + codec.getName());
                }
            }
        }
    }

}