package com.ambicious.accessChallege.config;

import com.ambicious.accessChallege.services.ConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;

@Configuration("CacheConfiguration")
@Profile("RedisSentinel")
public class RedisSentinelConfig implements CacheConfiguration {

    @Autowired
    ConfigService configService;

    @Override
    public RedisConnectionFactory getRedisConnectionFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        //set master sentinel
        redisSentinelConfiguration.setMaster(configService.getRedisMaster());
        //set password if it exists
        String password = configService.getRedisPassword();
        if (StringUtils.isNotEmpty(password)) {
            redisSentinelConfiguration.setPassword(RedisPassword.of(password));
        }
        //get all the sentinels, and add every one of them to sentinelConfig as redisNode
        List<String> sentinels = configService.getRedisSentinels();
        for (String sentinel : sentinels) {
            int delimiterIndex = sentinel.indexOf(':');
            String sentinelIp = sentinel.substring(0,delimiterIndex);
            String sentinelPort = sentinel.substring(delimiterIndex+1);
            RedisNode redisNode = new RedisNode(sentinelIp, Integer.parseInt(sentinelPort));
            redisSentinelConfiguration.addSentinel(redisNode);
        }
        RedisConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisSentinelConfiguration);
        return redisConnectionFactory;
    }
}
