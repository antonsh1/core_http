package ru.smartjava;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import ru.smartjava.classes.Cat;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        String catUrl = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        List<Cat> cats = new ArrayList<>();
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
//            String test = new String(EntityUtils.toByteArray(response.getEntity()), "UTF-8");
//            String resISO = EntityUtils.toString(response.getEntity());
//            String resUTF = new String(resISO.getBytes("UTF-8"), "UTF-8");
//            System.out.println(resUTF);
            cats = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {});
            cats.forEach(System.out::println);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        List<Cat> test = cats.stream().filter(cat -> cat.getUpvotes() > 0).collect(Collectors.toList());
//        System.out.println(cats.stream().filter(cat -> cat.getUpvotes() > 0).collect(Collectors.toList()));
//        System.out.println(cats.get(0).getText());
    }
}