package com.weather;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
// import jakarta.inject.Inject;
// import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import com.weather.response.Weather;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
import static io.restassured.RestAssured.*;

@QuarkusTest
class WeatherResourceTest {

    @Test
    public void testFetchWeatherDataEndpoint() {
        given()
                .queryParam("city", "London")
                .when().get("/weather")
                .then()
                .statusCode(200);
    }
    @Test
    public void testGetAllWeatherData() {
        Response response = given()
                .when().get("/weather/all")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.asString());
        assertThat(response.jsonPath().getList("$"), is(not(empty())));
    }

    @Test
    public void testGetWeatherDataByCity() {
        Response response = given()
                .pathParam("city", "London")
                .when().get("/weather/fetch/{city}")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.asString());
        assertThat(response.jsonPath().getList("$"), is(not(empty())));
        assertThat(response.jsonPath().getString("[0].city"), is("London"));
    }
    @Test
    public void testUpdateWeatherDataEndpoint() {
        Weather data = new Weather();
        data.setTemperature(283.49);
        data.setHumidity(400.15);

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("city", "London")
                .body(data)
                .when().put("/weather/{city}")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.asString());

        String status = response.jsonPath().getString("status");
        if ("error".equals(status)) {
            // Handle error case
            String errorMessage = response.jsonPath().getString("message");
            System.out.println("Error: " + errorMessage);
        } else {
            // Handle success case
            assertThat(status, is("success"));
            assertThat(response.jsonPath().getDouble("temperature"), is(283.49));
        }

    }
    @Test
    public void testDeleteWeatherDataEndpoint() {
        Response response = given()
                .pathParam("city", "London")
                .when().delete("/weather/{city}")
                .then()
                .extract().response();

        System.out.println(response.asString());

        String status = response.jsonPath().getString("status");
        if ("error".equals(status)) {
            // Handle error case
            String errorMessage = response.jsonPath().getString("message");
            System.out.println("Error: " + errorMessage);
        } else {
            // Handle success case
            assertThat(status, is("success"));
            assertThat(response.jsonPath().getString("message"), is("Weather data successfully deleted for city: London"));
            assertThat(response.jsonPath().get("data"), is(nullValue()));
        }

    }
}