package com.ambicious.accessChallege.dao;

import com.ambicious.accessChallege.error.ErrorCodes;
import com.ambicious.accessChallege.error.exception.CacheAccessException;
import com.ambicious.accessChallege.models.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheAccessRedisImpl.class);
    protected static final String CACHE_ACCESS_ERROR = "An error occurred during cache access.";
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
        RedisConnection redisConnection = null;
        byte[] value = null;
        try {
            redisConnection = cacheConfiguration.getRedisTemplate().getConnectionFactory().getConnection();
            //retrieve value.
            value = redisConnection.get(key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            LOGGER.error("Exception while retrieving cache value", e);
            ApiError apiError = new ApiError();
            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
            throw new CacheAccessException(CACHE_ACCESS_ERROR, apiError);
        } finally {
            closeConnection(redisConnection);
        }
        return value;
    }

    @Override
    public void saveObject(String key, byte[] object) {
        RedisConnection redisConnection = null;
        byte[] value = null;
        try {
            redisConnection = cacheConfiguration.getRedisTemplate().getConnectionFactory().getConnection();
            //save value.
            redisConnection.set(key.getBytes(StandardCharsets.UTF_8), object);
        } catch (Exception e) {
            LOGGER.error("Exception while retrieving cache value", e);
            ApiError apiError = new ApiError();
            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
            throw new CacheAccessException(CACHE_ACCESS_ERROR, apiError);
        } finally {
            closeConnection(redisConnection);
        }
    }

    @Override
    public void saveObject(String key, byte[] object, int timeToLive) {
        RedisConnection redisConnection = null;
        byte[] value = null;
        try {
            redisConnection = cacheConfiguration.getRedisTemplate().getConnectionFactory().getConnection();
            //save value.
            redisConnection.setEx(key.getBytes(StandardCharsets.UTF_8), timeToLive, object);
        } catch (Exception e) {
            LOGGER.error("Exception while retrieving cache value", e);
            ApiError apiError = new ApiError();
            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
            throw new CacheAccessException(CACHE_ACCESS_ERROR, apiError);
        } finally {
            closeConnection(redisConnection);
        }
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

    /**
     * Close the provided redis connection if it is open.
     *
     * @param conn Redis connection to close.
     */
    private void closeConnection(RedisConnection conn) {
        if (conn != null) {
            conn.close();
        }
    }
}
