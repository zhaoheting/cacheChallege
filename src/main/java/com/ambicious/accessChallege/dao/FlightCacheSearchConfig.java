//package com.openjaw.api.dao;
//
//import com.openjaw.api.services.config.ConfigService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
///**
// * Returns one redis template and one redis connection factory with sentinel config.
// *
// * @author zepeng.yang
// * @since 21/06/2019
// */
//@Configuration
//public class FlightCacheSearchConfig {
//    protected static final String REDIS_MODE_CLUSTER = "CLUSTER";
//    protected static final String REDIS_MODE_SENTINEL = "SENTINEL";
//    private static final String WRONG_REDIS_MODE =
//            "Couldn't init flight cache bean, please config your redis mode between 'CLUSTER' and 'SENTINEL'.";
//    protected ConfigService configService;
//    protected RedisSentinelConfig redisSentinelConfig;
//    protected RedisClusterConfig redisClusterConfig;
//
//    /**
//     * Constructor.
//     *
//     * @param configService Config service.
//     */
//    public FlightCacheSearchConfig(ConfigService configService) {
//        this.configService = configService;
//        redisSentinelConfig = new RedisSentinelConfig(configService);
//        redisClusterConfig = new RedisClusterConfig(configService);
//    }
//
//    /**
//     * Init one {@link RedisConnectionFactory} as bean.
//     *
//     * @return Bean of type {@link RedisConnectionFactory}.
//     */
//    @Bean
//    public RedisConnectionFactory flightCacheSearchRedisFactory() {
//        String redisMode = configService.getFlightCacheSearchRedisMode();
//        if (REDIS_MODE_CLUSTER.equals(redisMode)) {
//            return redisClusterConfig.redisConnectionFactory();
//        }
//        if (REDIS_MODE_SENTINEL.equals(redisMode)) {
//            return redisSentinelConfig.redisConnectionFactory();
//        }
//        throw new UnsupportedOperationException(WRONG_REDIS_MODE);
//    }
//
//    /**
//     * Init one {@link RedisTemplate} as bean.
//     *
//     * @return Bean of type {@link RedisTemplate}.
//     */
//    @Bean
//    public RedisTemplate<String, Object> flightCacheSearchRedisTemplate() {
//        return ((CacheConfiguration) this::flightCacheSearchRedisFactory).getRedisTemplate();
//    }
//}
