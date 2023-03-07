package ru.smartjava;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.smartjava.classes.Cat;
import ru.smartjava.classes.NasaResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String nasaUrl = "https://api.nasa.gov/planetary/apod?api_key=";
        String nasaApiKey = "dnEd6FiO1wZxed9JzzBOq9R7hMjjFQnGz7JZJz9X";
        String URI = nasaUrl + nasaApiKey;
        ObjectMapper mapper = new ObjectMapper();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .build())
                .build();

        HttpGet request = new HttpGet(URI);

        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        try {
            CloseableHttpResponse response = httpClient.execute(request);
            System.out.println(response.toString());
            System.out.println(Arrays.toString(response.getEntity().getContent().readAllBytes()));
            NasaResponse nasaResponse = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {});
            System.out.println(nasaResponse);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        cats.stream().filter(cat -> cat.getUpvotes() != null && cat.getUpvotes() > 0).forEach(System.out::println);

    }
}