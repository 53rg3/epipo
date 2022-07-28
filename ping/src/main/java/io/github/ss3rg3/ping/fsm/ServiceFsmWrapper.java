package io.github.ss3rg3.ping.fsm;

import io.github.ss3rg3.ping.websocket.FsmSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ServiceFsmWrapper {

    private final ServiceFSM INSTANCE;

    @Inject
    public ServiceFsmWrapper(FsmSession fsmSession) {
        ServiceFSM serviceFSM = new ServiceFSM();
        this.INSTANCE = serviceFSM.create(fsmSession);
    }

    public ServiceFSM get() {
        return this.INSTANCE;
    }
}
