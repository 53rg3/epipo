package io.github.ss3rg3.pong.endpoints;

import io.auto.generated.PingService;
import io.auto.generated.Pong;
import io.github.ss3rg3.pong.models.AppConfig;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

@Path("/")
public class IndexEndpoint {

    private final Template page;

    @Inject
    AppConfig appConfig;

    @GrpcClient("PingService")
    PingService pingService;

    public IndexEndpoint(Template page) {
        this.page = requireNonNull(page, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> get() {
        return Uni.createFrom().completionStage(CompletableFuture.supplyAsync(() -> {
            this.pingService.handlePong(Pong.getDefaultInstance()).await().indefinitely();
            return this.page.data("name", this.appConfig.name());
        }));
    }

}
