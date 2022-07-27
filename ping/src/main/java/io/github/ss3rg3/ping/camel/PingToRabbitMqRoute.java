package io.github.ss3rg3.ping.camel;

import io.auto.generated.Ping;
import io.github.ss3rg3.ping.config.CamelConfig;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.rabbitmq;

@Singleton
public class PingToRabbitMqRoute extends RouteBuilder {

    public static final String DIRECT_PING_PRODUCER = "direct:ping-producer";

    @Inject
    CamelConfig camelConfig;

    @Override
    public void configure() {
        this.from(DIRECT_PING_PRODUCER)
                .marshal().protobuf(Ping.getDefaultInstance(), "json")
                .to(rabbitmq(camelConfig.rabbitMq().exchange())
                        .hostname(camelConfig.rabbitMq().hostname())
                        .portNumber(camelConfig.rabbitMq().port())
                        .username(camelConfig.rabbitMq().username())
                        .password(camelConfig.rabbitMq().password())
                        .queue(camelConfig.rabbitMq().queue())
                        .autoDelete(false));
    }

}
