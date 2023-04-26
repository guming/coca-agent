package org.coca.agent.sample;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ExampleHttp {
    public static void main(String[] args) throws Exception {
//        HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://www.google.com").openConnection();
//        System.out.println(urlConnection.getRequestMethod());
//        DemoService demoService = new DemoService();
//        System.out.println(demoService.report("mgu",2 ));
        getClient();
    }

    public static void getClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(new HttpGet("https://www.baidu.com"));
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
