package io.github.ss3rg3.ping.websocket;

import io.github.ss3rg3.ping.fsm.ServiceFSM.ServiceState;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.io.IOException;

@ApplicationScoped
public class FsmSession {

    private static final Logger LOG = Logger.getLogger(FsmSession.class);

    private Session session;

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

    public void sendStateToClient(ServiceState serviceState) {
        if (this.session == null) {
            return;
        }
        if (!this.session.isOpen()) {
            return;
        }

        this.session.getAsyncRemote().sendObject(serviceState.name(), result -> {
            if (result.getException() != null) {
                LOG.error("Unable to send message: " + result.getException());
            }
        });
    }
}
