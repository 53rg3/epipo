package io.github.ss3rg3.pong.camel;

import io.github.ss3rg3.ping.Ping;
import io.github.ss3rg3.ping.PingServiceProto;
import io.github.ss3rg3.ping.Pong;
import io.github.ss3rg3.pong.utils.PROTO;
import io.quarkus.grpc.GrpcClient;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Singleton;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.*;

@Singleton
public class RabbitMqToPingServiceRoute extends RouteBuilder {

    @GrpcClient("pingServiceProto")
    PingServiceProto pingServiceProto;

    @Override
    public void configure() {
        this.from(rabbitmq("ping-exchange")
                        .hostname("localhost")
                        .portNumber(5672)
                        .queue("ping-queue")
                        .autoDelete(false))
                .process(this::createPong)
                .to(grpc("localhost:29000/io.github.ss3rg3.ping.PingServiceProto")
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
