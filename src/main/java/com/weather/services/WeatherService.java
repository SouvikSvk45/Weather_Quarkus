package com.weather.services;


import com.weather.OpenWeatherClient;
import com.weather.exceptionhandler.WeatherNotFoundException;
import com.weather.exceptionhandler.WeatherServiceException;
import com.weather.response.Weather;
import com.weather.response.WeatherResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// import java.sql.Timestamp;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.quarkus.logging.Log;

@ApplicationScoped
public class WeatherService {
    @Inject
    @RestClient
    OpenWeatherClient openWeatherClient;
    @Transactional
    // @Retry(maxRetries = 3, delay = 1000)
    // @Timeout(2000)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Weather fetchAndSaveWeather(String city) {
        
        try {
            String apiKey ="c3150f1219ff41b00e8b89ca29e3505a";
            WeatherResponse response = openWeatherClient.getWeather(city, apiKey);
            Weather weather = new Weather();
            weather.setCity(city);
            weather.setDescription(response.getWeather().get(0).getDescription());
            weather.setTemperature(response.getMain().getTemp());
            weather.setHumidity(response.getMain().humidity);
            weather.persist();
            return weather;
        } catch (Exception e) {
            Log.error("Error Message: "+ e.getMessage());
            Log.error("Error Cause: "+ e.getCause());
            throw new WeatherServiceException("Failed to fetch and save weather data for city: " + city, e);
        }
    }
    @Transactional
    @Retry(maxRetries = 3, delay = 1000)
    @Timeout(2000)
    

    public String updateWeather(Integer id, Weather updatedWeather) {
        try {
            Weather weather = Weather.find("id", id).firstResult();
            if (weather == null) {
                throw new WeatherNotFoundException("No records found for city_Id: " + id);
            } else {
                weather.setDescription(updatedWeather.getDescription());
                weather.setTemperature(updatedWeather.getTemperature());
                weather.setHumidity(updatedWeather.getHumidity());
                // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                // //weather.setTime(timestamp);
                weather.persist();

                Log.info("weather: " + weather);
                return "Weather data updated for city: " + id;
            }
        } catch (WeatherNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new WeatherServiceException("Failed to update weather data for city: " + id, e);
        }
    }
    

    @Transactional
    public String deleteWeather(Integer id) {
        Weather weather = Weather.find("id", id).firstResult();
        if (weather == null) {
            return "No records found for city_Id: " + id;
        } else {
            weather.delete();
            return "Weather data successfully deleted for city_Id: " + id;
        }
    }

}














// public String updateWeather(String city, Weather updatedWeather) {
    //     try {
    //         Weather weather = Weather.find("city", city).firstResult();
    //         if (weather == null) {
    //             throw new WeatherNotFoundException("No records found for city: " + city);
    //         } else {
    //             weather.setDescription(updatedWeather.getDescription());
    //             weather.setTemperature(updatedWeather.getTemperature());
    //             weather.setHumidity(updatedWeather.getHumidity());
    //             // weather.persist();
    //             Log.info("weather: " + weather);
    //             return "Weather data updated for city: " + city;
    //         }
    //     } catch (WeatherNotFoundException e) {
    //         throw e;
    //     } catch (Exception e) {
    //         throw new WeatherServiceException("Failed to update weather data for city: " + city, e);
    //     }
    // }

    // @Transactional
    // public String deleteWeather(String city) {
    //     Weather weather = Weather.find("city", city).firstResult();
    //     if (weather == null) {
    //         return "No records found for city: " + city;
    //     } else {
    //         weather.delete();
    //         return "Weather data successfully deleted for city: " + city;
    //     }
    // }