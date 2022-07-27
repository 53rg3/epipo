package io.github.ss3rg3.pong.config;

import io.github.ss3rg3.pong.utils.FileUtils;
import io.github.ss3rg3.pong.utils.JSON;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PingConfig {

    @Min(value = 1, message = "'pingsToSend' must be between 1-1000")
    @Max(value = 1000, message = "'pingsToSend' must be between 1-1000")
    @NotNull(message = "'pingsToSend' cannot be null")
    public Integer pingsToSend;

    public static PingConfig load() {
        return JSON.fromJson(FileUtils.loadAsString(Constants.CONFIG_JSON_PATH), PingConfig.class);
    }
}
