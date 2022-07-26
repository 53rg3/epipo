package io.github.ss3rg3.ping.models;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "camel")
public interface CamelConfig {

    RabbitMqConfig rabbitMq();

    interface RabbitMqConfig {
        String exchange();
        String hostname();
        int port();
        String username();
        String password();
        String queue();
    }

}
