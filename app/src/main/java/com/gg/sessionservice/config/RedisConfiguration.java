package com.gg.sessionservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisConfiguration {
    @JsonProperty
    private String host;
    @JsonProperty
    private Integer port;
}
