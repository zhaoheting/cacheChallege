package com.ambicious.accessChallege.services;

import com.ambicious.accessChallege.config.CacheAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Future;

@Service
public class ObjectAccessService {
    protected final CacheAccess cacheAccess;
    protected final ObjectMapper jsonMapper;
    protected final ConfigService configService;
    protected final CompressionService compressionService;

    /**
     * @param cacheAccess
     * @param jsonMapper
     * @param configService
     * @param compressionService
     */
    public ObjectAccessService(CacheAccess cacheAccess, ObjectMapper jsonMapper, ConfigService configService, CompressionService compressionService) {
        this.cacheAccess = cacheAccess;
        this.jsonMapper = jsonMapper;
        this.configService = configService;
        this.compressionService = compressionService;
    }

    /**
     * @param key
     * @param object
     * @throws JsonProcessingException
     */
    public void save(String key, Object object) throws JsonProcessingException {
        int timeToLive = configService.getCacheObjectTTLInSeconds();
        save(key, object, timeToLive);
    }

    /**
     * @param key
     * @param object
     * @param timeToLive
     * @throws JsonProcessingException
     */
    public void save(String key, Object object, int timeToLive) throws JsonProcessingException {
        byte[] bytes = jsonMapper.writeValueAsBytes(object);
        cacheAccess.saveObject(key, bytes, timeToLive);
    }

    /**
     * @param key
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T get(String key, Class<T> tClass) throws IOException {
        byte[] bytes = cacheAccess.getObject(key);
        return jsonMapper.readValue(bytes, tClass);
    }

    /**
     * @param key
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    @Async
    public Future<Boolean> saveAsync(String key, Object object) throws JsonProcessingException {
        save(key, object);
        return new AsyncResult<>(true);
    }

    /**
     * @param key
     * @param object
     * @param timeToLive
     * @return
     * @throws JsonProcessingException
     */
    @Async
    public Future<Boolean> saveAsync(String key, Object object, int timeToLive) throws JsonProcessingException {
        save(key, object, timeToLive);
        return new AsyncResult<>(true);
    }

    /**
     * @param key
     * @param object
     * @param timeToLive
     * @throws IOException
     */
    public void saveCompressed(String key, Object object, int timeToLive) throws IOException {
        byte[] bytes = compressionService.compressBytes(jsonMapper.writeValueAsBytes(object), configService.getCompressionLevel());
        cacheAccess.saveObject(key, bytes, timeToLive);
    }

    /**
     * @param key
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T getCompressed(String key, Class<T> tClass) throws IOException {
        byte[] bytes = cacheAccess.getObject(key);
        return jsonMapper.readValue(bytes, tClass);
    }
}
