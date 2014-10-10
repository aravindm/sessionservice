package com.gg.sessionservice.api;

import lombok.Data;

import java.util.Map;

@Data
public class Session {
    String id;
    Map<String, Object> data;
}
