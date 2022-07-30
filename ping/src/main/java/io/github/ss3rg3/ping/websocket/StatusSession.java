package io.github.ss3rg3.ping.websocket;

import io.github.ss3rg3.ping.health.HealthCollector;
import io.github.ss3rg3.ping.metrics.Meters;
import io.github.ss3rg3.ping.models.Status;
import io.github.ss3rg3.ping.utils.JSON;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import java.io.IOException;

@ApplicationScoped
public class StatusSession {

    private static final Logger LOG = Logger.getLogger(StatusSession.class);

    private Session session;

    @Inject
    HealthCollector healthCollector;

    @Inject
    Meters meters;

    public Session get() {
        return this.session;
    }

    public void set(Session session) {
        this.session = session;
    }

    public void close() {
        if (this.session != null) {
            try {
                this.session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendStatusToClient() {
        if (this.session == null) {
            return;
        }
        if (!this.session.isOpen()) {
            return;
        }
        String json = JSON.toJson(new Status(
                this.meters.getPingsSent().count(),
                this.meters.getPongsReceived().count(),
                this.meters.getPongPerSecond(),
                this.healthCollector.getCurrent()));

        this.session.getAsyncRemote().sendObject(json, result -> {
            if (result.getException() != null) {
                LOG.error("Unable to send message: " + result.getException());
            }
        });
    }
}
