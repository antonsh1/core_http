package ru.smartjava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.smartjava.classes.Cat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String catUrl = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        List<Cat> cats = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type catListType = new TypeToken<ArrayList<Cat>>(){}.getType();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet(catUrl);

        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            byte[] bytes = response.getEntity().getContent().readAllBytes();
//            System.out.println(new String(bytes));
            cats = gson.fromJson(new String(bytes), catListType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cats.stream().filter(cat -> cat.getUpvotes() != null && cat.getUpvotes() > 0).forEach(System.out::println);
    }
}