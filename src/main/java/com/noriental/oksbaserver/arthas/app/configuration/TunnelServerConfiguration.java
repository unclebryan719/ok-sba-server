package com.noriental.oksbaserver.arthas.app.configuration;

import com.noriental.oksbaserver.arthas.TunnelServer;
import com.noriental.oksbaserver.arthas.cluster.TunnelClusterStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author hengyunabc 2020-10-27
 *
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class TunnelServerConfiguration {

    @Autowired
    ArthasProperties arthasProperties;

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnMissingBean
    public TunnelServer tunnelServer(@Autowired(required = false) TunnelClusterStore tunnelClusterStore) {
        TunnelServer tunnelServer = new TunnelServer();

        tunnelServer.setHost(arthasProperties.getServer().getHost());
        tunnelServer.setPort(arthasProperties.getServer().getPort());
        tunnelServer.setSsl(arthasProperties.getServer().isSsl());
        tunnelServer.setPath(arthasProperties.getServer().getPath());
        tunnelServer.setClientConnectHost(arthasProperties.getServer().getClientConnectHost());
        if (tunnelClusterStore != null) {
            tunnelServer.setTunnelClusterStore(tunnelClusterStore);
        }
        return tunnelServer;
    }

}
