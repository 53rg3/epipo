package io.github.ss3rg3.ping.camel;

import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CamelRoutes {

    @Inject
    PingToRabbitMqRoute pingToRabbitMqRoute;

    @Inject
    TimerRoutes timerRoutes;

    public List<RouteBuilder> asList() {
        return List.of(
                this.pingToRabbitMqRoute,
                this.timerRoutes
        );
    }

}
