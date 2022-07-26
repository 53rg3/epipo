package io.github.ss3rg3.ping.models;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app")
public interface AppConfig {

    String name();

}
