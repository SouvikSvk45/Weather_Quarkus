
package com.weather;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Liveness
@Readiness
@ApplicationScoped
public class WeatherHealthCheck implements HealthCheck {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        try {
            entityManager.createQuery("SELECT 1").getSingleResult();
            return HealthCheckResponse.up("Database connection health check");
        } catch (Exception e) {
            return HealthCheckResponse.down("Database connection health check");
        }
    }


}

