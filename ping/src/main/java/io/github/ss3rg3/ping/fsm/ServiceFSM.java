package io.github.ss3rg3.ping.fsm;

import io.github.ss3rg3.ping.fsm.ServiceFSM.ServiceEvent;
import io.github.ss3rg3.ping.fsm.ServiceFSM.ServiceState;
import io.github.ss3rg3.ping.websocket.FsmSession;
import org.jboss.logging.Logger;
import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.AnonymousAction;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.annotation.ContextInsensitive;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

@ContextInsensitive
public class ServiceFSM extends AbstractStateMachine<ServiceFSM, ServiceState, ServiceEvent, Void> {

    private static final Logger LOG = Logger.getLogger(ServiceFSM.class);

    private FsmSession fsmSession;

    public enum ServiceState {
        UNINITIALIZED,
        UNAVAILABLE,
        AVAILABLE
    }

    public enum ServiceEvent {
        ALL_SERVICES_UP,
        SERVICE_WENT_DOWN
    }

    public ServiceFSM create(FsmSession fsmSession) {
        this.fsmSession = fsmSession;

        StateMachineBuilder<ServiceFSM, ServiceState, ServiceEvent, Void> builder = StateMachineBuilderFactory.create(
                ServiceFSM.class, ServiceState.class, ServiceEvent.class, Void.class);

        builder.externalTransition().from(ServiceState.UNINITIALIZED)
                .to(ServiceState.AVAILABLE)
                .on(ServiceEvent.ALL_SERVICES_UP)
                .perform(this.sendToWebSocket());
        builder.externalTransition().from(ServiceState.UNAVAILABLE)
                .to(ServiceState.AVAILABLE)
                .on(ServiceEvent.ALL_SERVICES_UP)
                .perform(this.sendToWebSocket());
        builder.externalTransition().from(ServiceState.AVAILABLE)
                .to(ServiceState.UNAVAILABLE)
                .on(ServiceEvent.SERVICE_WENT_DOWN)
                .perform(this.sendToWebSocket());

        ServiceFSM serviceFSM = builder.newStateMachine(ServiceState.UNINITIALIZED);
        serviceFSM.start();
        return serviceFSM;
    }

    Action<ServiceFSM, ServiceState, ServiceEvent, Void> sendToWebSocket() {
        return new AnonymousAction<>() {
            @Override
            public void execute(ServiceState from, ServiceState to, ServiceEvent event, Void context, ServiceFSM stateMachine) {
                fsmSession.sendStateToClient(to);
                LOG.info("Got event '" + event + "', transitioning from '" + from + "' to '" + to + "'");
            }
        };
    }

}
