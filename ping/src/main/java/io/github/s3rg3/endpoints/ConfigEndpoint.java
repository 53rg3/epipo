package io.github.s3rg3.endpoints;

import io.github.s3rg3.models.PingConfig;
import io.github.s3rg3.utils.FileUtils;
import io.github.s3rg3.utils.JSON;
import io.github.s3rg3.utils.UniResponse;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.github.s3rg3.config.Constants.CONFIG_JSON_PATH;

@Path("/config")
@ApplicationScoped
public class ConfigEndpoint {

    private volatile PingConfig pingConfig;

    public ConfigEndpoint() {
        this.pingConfig = PingConfig.load();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getConfig() {
        return UniResponse.ok(JSON.toJson(this.pingConfig));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> putConfig(@Valid PingConfig newPingConfig) {
        try {
            FileUtils.writeFile(JSON.toJson(newPingConfig), CONFIG_JSON_PATH);
            this.pingConfig = PingConfig.load();
        } catch (Exception e) {
            return UniResponse.as(400, "Failed to update config: " + e.getMessage());
        }
        return UniResponse.ok();
    }
}
