package io.github.ss3rg3.pong.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "camel")
public interface CamelConfig {

    RabbitMqConfig rabbitMq();
    GrpcConfig grpc();

    interface RabbitMqConfig {
        String exchange();
        String hostname();
        int port();
        String username();
        String password();
        String queue();
    }

    interface GrpcConfig {
        String path();
        String method();
    }

}
