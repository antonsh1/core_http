package ru.smartjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import ru.smartjava.classes.NasaResponse;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        String path = "src/main/resources/";
        String nasaUrl = "https://api.nasa.gov/planetary/apod?api_key=";
        String nasaApiKey = "dnEd6FiO1wZxed9JzzBOq9R7hMjjFQnGz7JZJz9X";
        String URI = nasaUrl + nasaApiKey;

        NasaResponse nasaResponse;
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .build())
                .build();

        HttpGet request = new HttpGet(URI);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //Обращаемся к ресурсу и преобразуем результат
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            nasaResponse = mapper.readValue(response.getEntity().getContent(), NasaResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Берем последнюю часть url с названием файла
        String fileName = nasaResponse.getUrl().split("/")[nasaResponse.getUrl().split("/").length - 1];

        //Сохраняем файл с картинкой
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(nasaResponse.getUrl()));
             InputStream in = response.getEntity().getContent();
        ) {
            Files.copy(in, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}