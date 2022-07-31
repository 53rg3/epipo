package io.github.ss3rg3.ping.endpoints;

import io.github.ss3rg3.ping.config.AppConfig;
import io.github.ss3rg3.ping.utils.UniResponse;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static java.util.Objects.requireNonNull;

@Path("/")
public class IndexEndpoint {

    private final Template index;

    @Inject
    AppConfig appConfig;

    public IndexEndpoint(Template index) {
        this.index = requireNonNull(index, "index.html is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> get() {
        return Uni.createFrom().item(index.data("name", appConfig.name()));
    }

}
