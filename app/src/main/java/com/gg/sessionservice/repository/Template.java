package com.gg.sessionservice.repository;

import java.util.Map;

public interface Template {
    Map<String, String> get(String key);

    boolean setExpiry(String sessionId, long ttl);

    boolean create(String key, Map<String, String> value);

    boolean update(String key, Map<String, String> value);
}
