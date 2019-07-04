package com.ambicious.accessChallege.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Heting Zhao
 */
@Component()
@Profile({"RedisCluster", "RedisSentinel"})
public class CacheAccessRedisImpl implements CacheAccess {

    protected CacheConfiguration cacheConfiguration;

    /**
     * Constructor.
     *
     * @param cacheConfiguration
     */
    public CacheAccessRedisImpl(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
    }

    @Override
    public byte[] getObject(String key) {
        return new byte[0];
    }

    @Override
    public void saveObject(String key, Object object) {
        RedisConnection redisConnection = cacheConfiguration.getRedisTemplate().getConnectionFactory().getConnection();
        redisConnection.get(key.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void saveObject(String key, Object object, int timeToLive) {

    }

    @Override
    public void deleteObject(String key) {

    }

    @Override
    public void containKey(String key) {

    }

    @Override
    public void setTimeToLive(int timeToLive) {

    }

    @Override
    public void getTimeToLive(String key) {

    }

    @Override
    public Boolean saveObjectIfAbsent(byte[] key, byte[] object, int timeToLive) {
        return null;
    }

    @Override
    public List<String> getHashValues(String key, String[] fields) {
        return null;
    }

    @Override
    public void setHashValues(String key, Map<String, String> map) {

    }
}
