package io.github.ss3rg3.ping.health;

import io.github.ss3rg3.ping.restclients.RabbitMqApiProxy;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class RabbitMqHealthCheck implements HealthCheck {

    private static final String RABBIT_MQ = "RabbitMQ";

    @RestClient
    RabbitMqApiProxy rabbitMqApiProxy;

    @Override
    public HealthCheckResponse call() {
        try {
            this.rabbitMqApiProxy.get();
            return HealthCheckResponse.up(RABBIT_MQ);
        } catch (Exception e) {
            return HealthCheckResponse.builder()
                    .down()
                    .name(RABBIT_MQ)
                    .withData("cause", e.getMessage())
                    .build();
        }
    }
}
