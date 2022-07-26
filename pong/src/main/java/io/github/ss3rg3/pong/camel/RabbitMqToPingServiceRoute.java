package io.github.ss3rg3.pong.camel;

import io.auto.generated.Ping;
import io.auto.generated.Pong;
import io.github.ss3rg3.pong.utils.PROTO;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Singleton;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.*;

@Singleton
public class RabbitMqToPingServiceRoute extends RouteBuilder {

    @Override
    public void configure() {
        this.from(rabbitmq("ping-exchange")
                        .hostname("localhost")
                        .portNumber(5672)
                        .queue("ping-queue")
                        .autoDelete(false))
                .process(this::createPong)
                .to(grpc("localhost:29000/io.auto.generated.PingService")
                        .method("handlePong"))
                .to(log("PongService").plain(true));
    }

    private void createPong(Exchange exchange) {
        Ping ping = PROTO.toProto(exchange.getIn().getBody(String.class), Ping.getDefaultInstance());

        Pong pong = Pong.newBuilder()
                .setCount(ping.getCount())
                .build();

        exchange.getIn().setBody(pong);
    }

}
