package com.ambicious.accessChallege.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@FunctionalInterface
public interface CacheConfiguration {
    Logger LOGGER = LoggerFactory.getLogger(CacheConfiguration.class);

    /**
     * @return
     */
    RedisConnectionFactory getRedisConnectionFactory();

    /**
     * @return
     */
    default RedisTemplate<String, Object> getRedisTemplate() {
        RedisTemplate<String, Object> template = null;
        try {
            RedisConnectionFactory factory = getRedisConnectionFactory();
            if (factory != null) {
                template = new RedisTemplate<>();
                template.setConnectionFactory(factory);
                template.setKeySerializer(new StringRedisSerializer());
                template.setValueSerializer(new StringRedisSerializer());
                template.setHashKeySerializer(new StringRedisSerializer());
                template.setHashValueSerializer(new StringRedisSerializer());
            } else {
                LOGGER.error("Failed to connect to redis.");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to connect to redis.", e);
        }
        return template;
    }

}
