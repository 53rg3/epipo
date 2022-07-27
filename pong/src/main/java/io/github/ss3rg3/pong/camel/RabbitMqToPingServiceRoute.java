package io.github.ss3rg3.pong.camel;

import io.auto.generated.Ping;
import io.auto.generated.Pong;
import io.github.ss3rg3.pong.config.CamelConfig;
import io.github.ss3rg3.pong.utils.PROTO;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.*;

@Singleton
public class RabbitMqToPingServiceRoute extends RouteBuilder {

    @Inject
    CamelConfig camelConfig;

    @Override
    public void configure() {
        this.from(rabbitmq(camelConfig.rabbitMq().exchange())
                        .hostname(camelConfig.rabbitMq().hostname())
                        .portNumber(camelConfig.rabbitMq().port())
                        .username(camelConfig.rabbitMq().username())
                        .password(camelConfig.rabbitMq().password())
                        .queue(camelConfig.rabbitMq().queue())
                        .autoDelete(false))
                .process(this::createPong)
                .to(grpc(camelConfig.grpc().path())
                        .method(camelConfig.grpc().method()));
    }

    private void createPong(Exchange exchange) {
        Ping ping = PROTO.toProto(exchange.getIn().getBody(String.class), Ping.getDefaultInstance());

        Pong pong = Pong.newBuilder()
                .setCount(ping.getCount())
                .build();

        exchange.getIn().setBody(pong);
    }

}
