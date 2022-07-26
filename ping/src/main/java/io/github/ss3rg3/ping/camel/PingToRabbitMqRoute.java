package io.github.ss3rg3.ping.camel;

import io.github.ss3rg3.ping.Ping;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Singleton;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.rabbitmq;

@Singleton
public class PingToRabbitMqRoute extends RouteBuilder {

    public static final String DIRECT_FOO = "direct:foo";

    @Override
    public void configure() {
        this.from(DIRECT_FOO)
                .marshal().protobuf(Ping.getDefaultInstance(), "json")
                .to(rabbitmq("ping-exchange")
                        .hostname("localhost")
                        .portNumber(5672)
                        .username("guest")
                        .password("guest")
                        .queue("ping-queue")
                        .autoDelete(false));
    }

}
