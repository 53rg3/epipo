package io.github.s3rg3.models;

import io.github.s3rg3.utils.FileUtils;
import io.github.s3rg3.utils.JSON;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static io.github.s3rg3.config.Constants.CONFIG_JSON_PATH;

public class PingConfig {

    @Min(value = 1, message = "'pingsToSend' must be between 1-1000")
    @Max(value = 1000, message = "'pingsToSend' must be between 1-1000")
    @NotNull(message = "'pingsToSend' cannot be null")
    public Integer pingsToSend;

    public static PingConfig load() {
        return JSON.fromJson(FileUtils.loadAsString(CONFIG_JSON_PATH), PingConfig.class);
    }
}
