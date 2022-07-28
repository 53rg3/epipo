package io.github.ss3rg3.ping.health;

import io.github.ss3rg3.ping.config.HealthConfig;
import io.github.ss3rg3.ping.fsm.ServiceFSM.ServiceEvent;
import io.github.ss3rg3.ping.fsm.ServiceFsmWrapper;
import io.github.ss3rg3.ping.models.Status.Health;
import io.github.ss3rg3.ping.utils.JSON;
import io.quarkus.scheduler.Scheduled;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;

@ApplicationScoped
public class HealthCollector {

    private Health current = Health.asEmpty();
    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .callTimeout(Duration.ofMillis(1000))
            .connectTimeout(Duration.ofMillis(1000))
            .readTimeout(Duration.ofMillis(1000))
            .build();

    @Inject
    HealthConfig healthConfig;

    @Inject
    ServiceFsmWrapper serviceFsm;

    public Health getCurrent() {
        return this.current;
    }

    private void setCurrent(Response response) {
        try {
            if (response.body() != null) {
                this.current = JSON.fromJson(response.body().string(), Health.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.updateServiceFSM();
    }

    private void updateServiceFSM() {
        switch (this.current.status) {
            case "UP":
                this.serviceFsm.get().fire(ServiceEvent.ALL_SERVICES_UP);
                break;
            case "DOWN":
            default:
                this.serviceFsm.get().fire(ServiceEvent.SERVICE_WENT_DOWN);
                break;
        }
    }

    @Scheduled(every = "1s")
    public void queryStatus() {
        Request request = new Request.Builder()
                .url(this.healthConfig.pingServiceHealthUrl())
                .build();

        Call call = this.httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                HealthCollector.this.setCurrent(response);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // NO OP
            }
        });
    }

}
