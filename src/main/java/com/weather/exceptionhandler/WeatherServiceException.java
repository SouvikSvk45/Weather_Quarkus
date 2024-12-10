package com.weather.exceptionhandler;

public class WeatherServiceException extends RuntimeException{
    public WeatherServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
