package com.weather.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.transaction.Transactional;

import java.util.List;

import com.weather.response.Weather;
@ApplicationScoped
public class WeatherDataRepository implements PanacheRepository<Weather> {
    public List<Weather> findByCity(String city) {
        return list("city", city);
    }
}
