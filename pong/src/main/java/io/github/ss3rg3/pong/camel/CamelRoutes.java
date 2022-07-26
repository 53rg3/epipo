package io.github.ss3rg3.pong.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CamelRoutes {

    @Inject
    RabbitMqToPingServiceRoute rabbitMqToPingServiceRoute;

    public List<RouteBuilder> asList() {
        return List.of(
                this.rabbitMqToPingServiceRoute
        );
    }

}
