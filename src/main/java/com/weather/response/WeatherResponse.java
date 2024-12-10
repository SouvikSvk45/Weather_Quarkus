package com.weather.response;

import java.util.List;

public class WeatherResponse {
    private List<WeatherInfo> weather;
    private Main main;

    // Getters and setters

    public List<WeatherInfo> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherInfo> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public static class WeatherInfo {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
// Getters and setters
    }

    public static class Main {
        // private int id;

        // public int getId() {
        //     return id;
        // }

        // public void setId(int id) {
        //     this.id = id;
        // }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double humidity;
        
        public double temp;
        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
// Getters and setters
    }
}
