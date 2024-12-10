package com.weather.resource;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
// import org.jboss.logging.Logger;
import com.weather.dao.WeatherDataRepository;
import com.weather.response.ApiResponse;
import com.weather.response.Weather;
import com.weather.services.WeatherService;
// import java.util.ArrayList;
import java.util.List;
 


@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WeatherResource {

    @Inject
    WeatherService weatherService;

    @Inject
    WeatherDataRepository repository;

    @GET
    @Path("/all")
    @Retry(maxRetries = 3, delay = 1000)
    @Timeout(2000)
    @Fallback(fallbackMethod = "fallbackListAllWeather")
    public List<Weather> getAllWeatherData() {
        // return repository.listAll();
        return repository.findAll().list();
        
    }

    @GET
    @Path("/{city}")
    @Retry(maxRetries = 3, delay = 1000)
    @Timeout(2000)
    @Fallback(fallbackMethod = "fallbackListAllWeather")
    //@PathParam("city") String city
    public List<Weather> gWeathers() {
        // return repository.findByCity("city"+city);
        return repository.findByCity("kolkata");
    }
    public List<Weather> fallbackListAllWeather() {
        return List.of();
    }

    @GET
    @Path("/fetch/{city}")
    public Weather getWeatherDataByCity(@PathParam("city") String city) {
        //return repository.findByCity(city);
        return weatherService.fetchAndSaveWeather(city);
    }

  
    @PUT
    @Path("/{id}")
    public ApiResponse updateWeather(@PathParam("id") Integer id, Weather weather) {
        String message = weatherService.updateWeather(id, weather);
        if (message.startsWith("No records found")) {
            return new ApiResponse("error", message);
        } else {
            return new ApiResponse("success", message);
        }
    }


    @DELETE
    @Path("/{id}")
    public ApiResponse  deleteWeather(@PathParam("id") Integer id) {
        String message = weatherService.deleteWeather(id);
        if (message.startsWith("No records found")) {
            return new ApiResponse("error", message);
        } else {
            return new ApiResponse("success", message);
        }
    }

}











// @GET
// @Retry(maxRetries = 3, delay = 1000)
// @Timeout(1000)
// public Weather getWeather(@QueryParam("city") String city) {
//     //LOG.info("Received city: " + city);
//     return weatherService.fetchAndSaveWeather(city);
// }

    //
// @GET
// @Path("/{city}")
// public Response getCityById(@PathParam("city") String city){
//     Optional<Weather> weatherOptional =  weatherList.stream()
//         .filter(weather -> weather.getCity()== city)
//         .findFirst();

//     if (weatherOptional.isPresent()) {
//         return Response.ok(weatherOptional.get()).build();
//     }else{
//         return Response.status(Response.Status.BAD_REQUEST).build();
//     }
// }
//

    // @GET
    // @Retry(maxRetries = 3, delay = 1000)
    // @Timeout(1000)
    // public Weather getWeather(@QueryParam("city") String city) {
    //     LOG.info("Received city: " + city);
    //     return weatherService.fetchAndSaveWeather(city);
    // }

 // @DELETE
    // @Path("/{city}")
    // public ApiResponse  deleteWeather(@PathParam("city") String city) {
    //     String message = weatherService.deleteWeather(city);
    //     if (message.startsWith("No records found")) {
    //         return new ApiResponse("error", message);
    //     } else {
    //         return new ApiResponse("success", message);
    //     }
    // }

        // @PUT
    // @Path("/{city}")
    // public ApiResponse updateWeather(@PathParam("city") String city, Weather weather) {
    //     String message = weatherService.updateWeather(city, weather);
    //     if (message.startsWith("No records found")) {
    //         return new ApiResponse("error", message);
    //     } else {
    //         return new ApiResponse("success", message);
    //     }
    // }
