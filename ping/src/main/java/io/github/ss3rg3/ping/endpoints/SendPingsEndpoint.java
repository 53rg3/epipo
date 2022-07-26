package io.github.ss3rg3.ping.endpoints;

import io.auto.generated.Ping;
import io.github.ss3rg3.ping.camel.CamelBean;
import io.github.ss3rg3.ping.models.PingConfig;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static io.github.ss3rg3.ping.camel.PingToRabbitMqRoute.DIRECT_FOO;

@Path("/send-pings")
public class SendPingsEndpoint {

    private final static AtomicInteger pingCount = new AtomicInteger();

    @Inject
    CamelBean camelBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> sendPings() {
        return Uni.createFrom().item(() -> {
            PingConfig pingConfig = PingConfig.load();

            IntStream.range(0, pingConfig.pingsToSend).forEach(count -> {
                Ping ping = Ping.newBuilder()
                        .setCount(pingCount.incrementAndGet())
                        .build();
                this.camelBean.to(DIRECT_FOO)
                        .withBody(ping)
                        .send();
            });

            return Response.ok("sending pings").build();
        });
    }

}
