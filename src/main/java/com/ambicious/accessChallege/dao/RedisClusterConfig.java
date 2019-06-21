package com.ambicious.accessChallege.dao;

import com.ambicious.accessChallege.services.ConfigService;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisClusterConfig implements CacheConfiguration {
    @Autowired
    ConfigService configService;

    @Override
    public RedisConnectionFactory getRedisConnectionFactory() {
        int refreshInterval = configService.getRedisRefreshInterval();
        boolean dynamicRefreshSources = configService.getRedisDynamicRefreshSources();
        boolean validateClusterMembership = configService.getRedisValidateClusterMembership();
        String redisPassword = configService.getRedisPassword();

        /*
        In case of failover within the cluster, the client may need to refresh it's knowledge of the
        cluster nodes. The periodic refresh interval defaults to 1 minute
         */
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh(Duration.ofSeconds(refreshInterval))
                .dynamicRefreshSources(dynamicRefreshSources)
                .build();

        ClientOptions clientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(topologyRefreshOptions)
                .validateClusterNodeMembership(validateClusterMembership)
                .build();

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(configService.getRedisClusterNodes());

        if (StringUtils.isNotEmpty(redisPassword)) {
            redisClusterConfiguration.setPassword(RedisPassword.of(redisPassword));
        }
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder().clientOptions(clientOptions).build();
        return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
    }
}
