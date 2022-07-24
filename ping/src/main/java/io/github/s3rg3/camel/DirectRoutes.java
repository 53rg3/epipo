package io.github.s3rg3.camel;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Inject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.*;

public class DirectRoutes extends RouteBuilder {

    private static final String FOO_ENDPOINT = "foo";
    public static final String DIRECT_FOO = "direct:foo";

    @Override
    public void configure() {
        this.from(direct(FOO_ENDPOINT))
                .process(this::transformToLogMessage)
                .to(log(DIRECT_FOO).plain(true))
                .to(rabbitmq("ping-exchange")
                        .hostname("localhost")
                        .portNumber(5672)
                        .queue("ping-queue")
                        .autoDelete(false));
    }

    private void transformToLogMessage(Exchange exchange) {
        exchange.getIn().setBody("Hello " + exchange.getIn().getBody());
    }
}
