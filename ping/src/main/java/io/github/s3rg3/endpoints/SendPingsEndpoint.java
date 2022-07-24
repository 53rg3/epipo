package io.github.s3rg3.endpoints;

import io.github.s3rg3.camel.DirectRoutes;
import io.github.s3rg3.utils.UniResponse;
import io.smallrye.mutiny.Uni;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static java.util.Optional.ofNullable;

@Path("/send-pings")
@ApplicationScoped
public class SendPingsEndpoint {

    @EndpointInject(DirectRoutes.DIRECT_FOO)
    private ProducerTemplate producerTemplate;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> putConfig(@QueryParam("name") String name) {
        this.producerTemplate.sendBody(ofNullable(name).orElse("Nobody"));
        return UniResponse.ok("sending pings");
    }

}
