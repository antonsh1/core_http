package ru.smartjava.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaResponse {
    @JsonProperty("date")
    private String date;
    @JsonProperty("explanation")
    private String explanation;
    @JsonProperty("hdurl")
    private String hdUrl;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("service_version")
    private String serviceVersion;
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
                ", date='" + date + '\'' +
                ", explanation='" + explanation + '\'' +
                ", hdUrl='" + hdUrl + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
