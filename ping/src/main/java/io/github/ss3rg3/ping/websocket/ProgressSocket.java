package io.github.ss3rg3.ping.websocket;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/progress")
@ApplicationScoped
public class ProgressSocket {

    private static final Logger LOG = Logger.getLogger(ProgressSocket.class);

    @Inject
    ProgressSession progressSession;

    @OnOpen
    public void onOpen(Session session) {
        this.progressSession.set(session);
    }

    @OnClose
    public void onClose(Session session) {
        this.progressSession.close();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        this.progressSession.close();
    }

    @OnMessage
    public void onMessage(String message) {
        // NO OP
    }

}
