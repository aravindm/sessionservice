package com.gg.sessionservice.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private String id;
    private Long ttl;
    private Map<String, String> data;
}
