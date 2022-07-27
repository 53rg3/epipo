package io.github.ss3rg3.ping.grpc;


import io.auto.generated.Empty;
import io.auto.generated.PingService;
import io.auto.generated.Pong;
import io.github.ss3rg3.ping.websocket.ProgressSession;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import javax.inject.Inject;

@GrpcService
public class PingServiceImpl implements PingService {

    private static final Logger LOG = Logger.getLogger(PingServiceImpl.class);

    @Inject
    ProgressSession progressSession;

    @Override
    public Uni<Empty> handlePong(Pong request) {
        LOG.error("Received pong: " + request.getCount());
        this.sendToProgressionSession(String.valueOf(request.getCount()));
        return Uni.createFrom().nullItem();
    }

    private void sendToProgressionSession(String message) {
        this.progressSession.get().getAsyncRemote().sendObject(message, result -> {
            if (result.getException() != null) {
                LOG.error("Unable to send message: " + result.getException());
            }
        });
    }
}
