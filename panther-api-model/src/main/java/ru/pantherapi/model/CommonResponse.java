package ru.pantherapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static org.springframework.http.HttpStatus.OK;

@Data
@SuppressWarnings("unused")
public class CommonResponse<T>
        implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5510024132679833954L;

    @JsonProperty("data")
    private T data;

    @JsonProperty("statusCode")
    private HttpStatus statusCode;

    @JsonProperty("errorDescription")
    private String errorDescription;

    private CommonResponse(T data) {
        this.data = data;
        this.statusCode = OK;
    }

    private CommonResponse(HttpStatus statusCode, String errorDescription) {
        this.data = null;
        this.statusCode = statusCode;
        this.errorDescription = errorDescription;
    }

    private CommonResponse(T data, HttpStatus statusCode, String errorDescription) {
        this.data = data;
        this.statusCode = statusCode;
        this.errorDescription = errorDescription;
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(data);
    }

    public static <T> CommonResponse<T> failed(HttpStatus statusCode, String errorDescription) {
        return new CommonResponse<>(statusCode, errorDescription);
    }

    public static <T> CommonResponse<T> failedWithData(T data, HttpStatus statusCode, String errorDescription) {
        return new CommonResponse<>(data, statusCode, errorDescription);
    }
}
