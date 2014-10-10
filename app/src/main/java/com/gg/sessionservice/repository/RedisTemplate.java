package com.gg.sessionservice.repository;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

import static java.lang.String.format;

@AllArgsConstructor
@CommonsLog
public class RedisTemplate implements Template {
    private JedisPool jedisPool;

    @Override
    public Map<String, String> get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(key);
        }
    }

    @Override
    public boolean setExpiry(String sessionId, long ttl) {
        try (Jedis jedis = jedisPool.getResource()) {
            Long expireResponse = jedis.pexpire(sessionId, ttl);
            return expireResponse == 1;
        }
    }

    @Override
    public boolean create(String key, Map<String, String> value) {
        try (Jedis jedis = jedisPool.getResource()) {
            Boolean exists = jedis.exists(key);
            if(exists) {
                log.error(format("Cannot create duplicate session : %s", key));
                return false;
            }
            String response = jedis.hmset(key, value);
            return response.equals("OK");
        }
    }

    @Override
    public boolean update(String key, Map<String, String> value) {
        try (Jedis jedis = jedisPool.getResource()) {
            Boolean exists = jedis.exists(key);
            if(exists) {
                log.error(format("Existing session has expired : %s", key));
                return false;
            }
            String response = jedis.hmset(key, value);
            return response.equals("OK");
        }
    }
}
