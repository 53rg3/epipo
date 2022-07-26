package io.github.ss3rg3.ping.grpc;


import io.auto.generated.Empty;
import io.auto.generated.PingService;
import io.auto.generated.Pong;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class PingServiceImpl implements PingService {

    @Override
    public Uni<Empty> handlePong(Pong request) {
        System.out.println("Received pong: "+request.getCount());
        return Uni.createFrom().nullItem();
    }
}
