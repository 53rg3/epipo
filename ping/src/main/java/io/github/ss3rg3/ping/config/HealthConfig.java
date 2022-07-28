package io.github.ss3rg3.ping.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "health")
public interface HealthConfig {

    String pingServiceHealthUrl();

}
