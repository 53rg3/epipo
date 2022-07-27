package io.github.ss3rg3.pong.endpoints;

import io.github.ss3rg3.pong.config.AppConfig;
import io.github.ss3rg3.pong.utils.UniResponse;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static java.util.Objects.requireNonNull;

@Path("/")
public class IndexEndpoint {

    @Inject
    AppConfig appConfig;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<Response> get() {
        return UniResponse.ok(appConfig.name() + " Service");
    }

}
