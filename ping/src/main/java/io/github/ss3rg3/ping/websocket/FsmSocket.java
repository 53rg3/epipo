package io.github.ss3rg3.ping.websocket;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/fsm")
@ApplicationScoped
public class FsmSocket {

    private static final Logger LOG = Logger.getLogger(FsmSocket.class);

    @Inject
    FsmSession fsmSession;

    @OnOpen
    public void onOpen(Session session) {
        this.fsmSession.set(session);
    }

    @OnClose
    public void onClose(Session session) {
        this.fsmSession.close();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        this.fsmSession.close();
    }

    @OnMessage
    public void onMessage(String message) {
        // NO OP
    }

}
