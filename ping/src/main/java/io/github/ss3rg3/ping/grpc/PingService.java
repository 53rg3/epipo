package io.github.ss3rg3.ping.grpc;

import io.github.ss3rg3.ping.*;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class PingService implements PingServiceProto {

    @Override
    public Uni<Empty> handlePong(Pong request) {
        System.out.println("Received pong: "+request.getCount());
        return Uni.createFrom().nullItem();
    }

}
