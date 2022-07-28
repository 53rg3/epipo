package io.github.ss3rg3.ping.grpc;


import io.auto.generated.Empty;
import io.auto.generated.PingService;
import io.auto.generated.Pong;
import io.github.ss3rg3.ping.websocket.StatusSession;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import javax.inject.Inject;

@GrpcService
public class PingServiceImpl implements PingService {

    private static final Logger LOG = Logger.getLogger(PingServiceImpl.class);

    @Inject
    StatusSession statusSession;

    @Override
    public Uni<Empty> handlePong(Pong request) {
        LOG.info("Received pong: " + request.getCount());
        this.statusSession.incrementPongCount();
        return Uni.createFrom().nullItem();
    }


}
