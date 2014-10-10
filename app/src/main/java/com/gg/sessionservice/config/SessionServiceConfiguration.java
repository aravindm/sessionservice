package com.gg.sessionservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionServiceConfiguration extends Configuration{
    @JsonProperty("redis")
    private RedisConfiguration redisConfiguration;
}
