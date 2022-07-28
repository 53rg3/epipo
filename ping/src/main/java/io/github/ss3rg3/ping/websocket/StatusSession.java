package io.github.ss3rg3.ping.websocket;

import io.github.ss3rg3.ping.health.HealthCollector;
import io.github.ss3rg3.ping.models.Status;
import io.github.ss3rg3.ping.utils.JSON;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class StatusSession {

    private static final Logger LOG = Logger.getLogger(StatusSession.class);

    private Session session;
    private final AtomicInteger pongCount = new AtomicInteger();

    @Inject
    HealthCollector healthCollector;

    public Session get() {
        return this.session;
    }

    public void set(Session session) {
        this.pongCount.set(0);
        this.session = session;
    }

    public void close() {
        this.pongCount.set(0);
        if (this.session != null) {
            try {
                this.session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void incrementPongCount() {
        this.pongCount.incrementAndGet();
    }

    public int getPongCount() {
        return this.pongCount.get();
    }

    public void sendStatusToClient() {
        if (this.session == null) {
            return;
        }
        if (!this.session.isOpen()) {
            return;
        }
        String json = JSON.toJson(new Status(
                this.pongCount.get(),
                healthCollector.getCurrent()));

        this.session.getAsyncRemote().sendObject(json, result -> {
            if (result.getException() != null) {
                LOG.error("Unable to send message: " + result.getException());
            }
        });
    }
}
