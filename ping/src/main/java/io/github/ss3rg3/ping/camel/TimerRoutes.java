package io.github.ss3rg3.ping.camel;

import io.github.ss3rg3.ping.websocket.StatusSession;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.timer;

@Singleton
public class TimerRoutes extends RouteBuilder {

    @Inject
    StatusSession statusSession;

    @Override
    public void configure() {
        this.from(timer("progress").period(1000))
                .process(exchange -> this.statusSession.sendStatusToClient());
    }

}
