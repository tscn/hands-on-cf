package com.github.tscn.cf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ServiceScan
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {

    @Bean
    public ApplicationInstanceInfo applicationInfo() {
        return cloud().getApplicationInstanceInfo();
    }

    @Bean
    @ConditionalOnProperty("cloud.services.redis.connection.uri")
    public MessageStore store() {
        return new MessageStore();
    }

}