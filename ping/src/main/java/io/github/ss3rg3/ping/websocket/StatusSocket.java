package io.github.ss3rg3.ping.websocket;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/status")
@ApplicationScoped
public class StatusSocket {

    private static final Logger LOG = Logger.getLogger(StatusSocket.class);

    @Inject
    StatusSession statusSession;

    @OnOpen
    public void onOpen(Session session) {
        this.statusSession.set(session);
    }

    @OnClose
    public void onClose(Session session) {
        this.statusSession.close();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        this.statusSession.close();
    }

    @OnMessage
    public void onMessage(String message) {
        // NO OP
    }

}
