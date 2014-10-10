package com.gg.sessionservice;

import com.gg.sessionservice.config.RedisConfiguration;
import com.gg.sessionservice.config.SessionServiceConfiguration;
import com.gg.sessionservice.controllers.SessionController;
import com.gg.sessionservice.repository.RedisTemplate;
import com.gg.sessionservice.repository.SessionRepository;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SessionService extends Application<SessionServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new SessionService().run(args);
    }

    @Override
    public void initialize(Bootstrap<SessionServiceConfiguration> bootstrap) {

    }

    @Override
    public void run(SessionServiceConfiguration configuration, Environment environment) throws Exception {
        RedisConfiguration redisConfiguration = configuration.getRedisConfiguration();
        //TODO: configurize maxidle minidle and maxtotal
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        JedisPool jedis = new JedisPool(poolConfig, redisConfiguration.getHost(), redisConfiguration.getPort());
        RedisTemplate redisTemplate = new RedisTemplate(jedis);
        SessionRepository sessionRepository = new SessionRepository(redisTemplate);
        SessionController sessionController = new SessionController(sessionRepository);
        environment.jersey().register(sessionController);
    }
}
