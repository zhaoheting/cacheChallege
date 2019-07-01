package com.ambicious.accessChallege.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
//@RefreshScope
public class ConfigService {
    @Value("${redis.refreshInterval:60}")
    private int redisRefreshInterval;
    @Value("${redis.dynamicRefreshSources:true}")
    private Boolean redisDynamicRefreshSources;
    @Value("${redis.validateClustermembership:true}")
    private Boolean redisValidateClusterMembership;
    @Value("${redis.password:#{null}}")
    private String redisPassword;
    @Value("#{T(java.util.Arrays).asList('${redis.cluster.nodes:}')}")
    private List<String> redisClusterNodes;
    //redis.cluster.nodes=team-oyster-db:7001,team-oyster-db:7002,team-oyster-db:7003
    @Value("#{T(java.util.Arrays).asList('${redis.sentinels:}')}")
    private List<String> redisSentinels;
    @Value("${redis.master:#{null}}")
    private String redisMaster;

    public int getRedisRefreshInterval() {
        return redisRefreshInterval;
    }

    public Boolean getRedisDynamicRefreshSources() {
        return redisDynamicRefreshSources;
    }

    public Boolean getRedisValidateClusterMembership() {
        return redisValidateClusterMembership;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public List<String> getRedisSentinels() {
        return Collections.unmodifiableList(redisSentinels);
    }

    /**
     * Used for the Profile RedisCluster.
     *
     * @return The Redis cluster nodes from the tRetailAPIConfig properties.
     */
    public List<String> getRedisClusterNodes() {
        return Collections.unmodifiableList(redisClusterNodes);
    }

    public String getRedisMaster() {
        return redisMaster;
    }
}
