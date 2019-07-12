//package com.ambicious.accessChallege.config;
//
//import com.ambicious.accessChallege.error.ErrorCodes;
//import com.ambicious.accessChallege.error.exception.CacheAccessException;
//import com.ambicious.accessChallege.models.ApiError;
//import com.ambicious.accessChallege.services.ConfigService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.redis.connection.convert.Converters;
//import org.springframework.stereotype.Component;
//import io.codis.jodis.RoundRobinJedisPool;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.Map;
//
//import static com.ambicious.accessChallege.error.ErrorCodes.ERROR_CACHE_ACCESS;
//
//@Component
//@Profile("Codis")
//public class CacheAccessCodisImpl implements CacheAccess {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CacheAccessCodisImpl.class);
//    protected static final String REDIS_SET_STRATEGY_NX = "NX";
//    protected static final String REDIS_SET_STRATEGY_EX = "EX";
//    protected static final String ERROR_MESSAGE_FOR_SETTING_VALUES = "Exception while setting cache value";
//    protected RoundRobinJedisPool jedisPool;
//    protected ConfigService configService;
//
//    /**
//     * Inject config service into this component.
//     *
//     * @param configService Config service.
//     */
//    public CacheAccessCodisImpl(ConfigService configService) {
//        this.configService = configService;
//
//        String codisZooKeeperAddress = configService.getCodisZooKeeperAddress();
//        String codisZooKeeperProxyPath = configService.getCodisZooKeeperProxyPath();
//        String codisServerPassword = configService.getCodisServerPassword();
//        int jodisPoolMaxTotal = configService.getJodisPoolMaxTotal();
//        int jodisPoolMaxIdle = configService.getJodisPoolMaxIdle();
//        int jodisPoolMinIdle = configService.getJodisPoolMinIdle();
//        long jodisPoolMaxWaitMillis = configService.getJodisPoolMaxWaitMillis();
//        int zooKeeperSessionTimeoutMillis = configService.getCodisZooKeeperSessionTimeoutMillis();
//
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(jodisPoolMaxTotal);
//        jedisPoolConfig.setMaxIdle(jodisPoolMaxIdle);
//        jedisPoolConfig.setMinIdle(jodisPoolMinIdle);
//        jedisPoolConfig.setMaxWaitMillis(jodisPoolMaxWaitMillis);
//        jedisPool = RoundRobinJedisPool.create().curatorClient(codisZooKeeperAddress, zooKeeperSessionTimeoutMillis).
//                zkProxyDir(codisZooKeeperProxyPath).poolConfig(jedisPoolConfig).password(codisServerPassword).build();
//    }
//
//    @Override
//    public byte[] getObject(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return jedis.get(key.getBytes(StandardCharsets.UTF_8));
//        } catch (Exception ex) {
//            LOGGER.error("Exception while retrieving cache value", ex);
//            ApiError apiError = new ApiError();
//            apiError.setErrorCode(ERROR_CACHE_ACCESS);
//            throw new CacheAccessException(ERROR_CACHE_ACCESS, apiError);
//        }
//    }
//
//    @Override
//    public void saveObject(String key, byte[] object, int timeToLive) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.setex(key.getBytes(StandardCharsets.UTF_8), timeToLive, object);
//        } catch (Exception ex) {
//            LOGGER.error(ERROR_MESSAGE_FOR_SETTING_VALUES, ex);
//            ApiError apiError = new ApiError();
//            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
//            throw new CacheAccessException(ERROR_CACHE_ACCESS, apiError);
//        }
//    }
//
//    @Override
//    public void saveObject(String key, byte[] object) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.set(key.getBytes(StandardCharsets.UTF_8), object);
//        } catch (Exception ex) {
//            LOGGER.error(ERROR_MESSAGE_FOR_SETTING_VALUES, ex);
//            ApiError apiError = new ApiError();
//            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
//            throw new CacheAccessException(ERROR_CACHE_ACCESS, apiError);
//        }
//    }
//
//    @Override
//    public void deleteObject(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.del(key);
//        } catch (Exception ex) {
//            LOGGER.error("Exception while deleting cache value", ex);
//            ApiError apiError = new ApiError();
//            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
//            throw new CacheAccessException(ERROR_CACHE_ACCESS, apiError);
//        }
//    }
//
//    @Override
//    public boolean containKey(String key) {
//        return false;
//    }
//
//    @Override
//    public void setTimeToLive(int timeToLive) {
//
//    }
//
//    @Override
//    public boolean containsKey(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return jedis.exists(key);
//        } catch (Exception ex) {
//            LOGGER.error("Exception while accessing cache", ex);
//            ApiError apiError = new ApiError();
//            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
//            throw new CacheAccessException(ERROR_CACHE_ACCESS, apiError);
//        }
//    }
//
//    @Override
//    public void setTimeToLive(String key, int timeToLive) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.expire(key, timeToLive);
//        }
//    }
//
//    @Override
//    public long getTimeToLive(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return jedis.ttl(key);
//        }
//    }
//
//    @Override
//    public List<String> getHashValues(String key, String[] fields) {
//        List<String> objects;
//        try (Jedis jedis = jedisPool.getResource()) {
//            objects = jedis.hmget(key, fields);
//        } catch (Exception ex) {
//            LOGGER.error("Exception while getting cache value", ex);
//            ApiError apiError = new ApiError();
//            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
//            throw new CacheAccessException(ERROR_CACHE_ACCESS, apiError);
//        }
//        return objects;
//    }
//
//    @Override
//    public Boolean saveObjectIfAbsent(byte[] key, byte[] object, int timeToLive) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return Converters.stringToBoolean(jedis.set(key, object, REDIS_SET_STRATEGY_NX.getBytes(StandardCharsets.UTF_8),
//                    REDIS_SET_STRATEGY_EX.getBytes(StandardCharsets.UTF_8), timeToLive));
//        } catch (Exception ex) {
//            LOGGER.error(ERROR_MESSAGE_FOR_SETTING_VALUES, ex);
//            ApiError apiError = new ApiError();
//            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
//            throw new CacheAccessException(ERROR_CACHE_ACCESS, apiError);
//        }
//    }
//
//    @Override
//    public void saveHashValues(String key, Map<String, String> map) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.hmset(key, map);
//        } catch (Exception ex) {
//            LOGGER.error(ERROR_MESSAGE_FOR_SETTING_VALUES, ex);
//            ApiError apiError = new ApiError();
//            apiError.setErrorCode(ErrorCodes.ERROR_CACHE_ACCESS);
//            throw new CacheAccessException(ERROR_CACHE_ACCESS, apiError);
//        }
//    }
//}
//
