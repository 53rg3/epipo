package io.github.s3rg3.models;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app")
public interface AppConfig {

    String name();

}
