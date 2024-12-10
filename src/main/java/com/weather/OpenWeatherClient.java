package com.weather;

// import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.weather.response.WeatherResponse;


@Path("/data/2.5/weather")
@RegisterRestClient(baseUri = "https://api.openweathermap.org")
public interface OpenWeatherClient {

    @GET
    WeatherResponse getWeather(@QueryParam("q") String city, @QueryParam("appid") String apiKey);
}