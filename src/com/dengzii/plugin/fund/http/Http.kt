package com.dengzii.plugin.fund.http;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Http {

    private final CloseableHttpClient client;

    public Http() {
        client = HttpClientBuilder.create()
                .build();
    }

    public void get(String url) {
        HttpUriRequest r = RequestBuilder.get(url).build();
        try {
            client.execute(r, httpResponse -> {
                InputStream inputStream = httpResponse.getEntity().getContent();
                InputStreamReader rd = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(rd);
                while (true){
                    String s = br.readLine();
                    if (s == null || s.isEmpty()) {
                        break;
                    }
                    System.out.println(s);
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void post() {

    }
}
