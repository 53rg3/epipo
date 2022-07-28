package io.github.ss3rg3.ping.health;

import io.github.ss3rg3.ping.restclients.PongServiceApiProxy;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class PongServiceHealthCheck implements HealthCheck {

    private static final String PONG_SERVICE = "Pong Service";

    @RestClient
    PongServiceApiProxy pongServiceApiProxy;

    @Override
    public HealthCheckResponse call() {
        try {
            this.pongServiceApiProxy.get();
            return HealthCheckResponse.up(PONG_SERVICE);
        } catch (Exception e) {
            return HealthCheckResponse.builder()
                    .down()
                    .name(PONG_SERVICE)
                    .withData("cause", e.getMessage())
                    .build();
        }
    }
}
