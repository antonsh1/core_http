package ru.smartjava.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaResponse {
    @JsonProperty("copyright")
    private String copyright;
    @JsonProperty("date")
    private String date;
    @JsonProperty("explanation")
    private String explanation;
    @JsonProperty("hdurl")
    private String hdurl;
    @JsonProperty("media_type")
    private String media_type;
    @JsonProperty("service_version")
    private String service_version;
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "NasaResponse{" +
                "copyright='" + copyright + '\'' +
                ", date='" + date + '\'' +
                ", explanation='" + explanation + '\'' +
                ", hdurl='" + hdurl + '\'' +
                ", media_type='" + media_type + '\'' +
                ", service_version='" + service_version + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
