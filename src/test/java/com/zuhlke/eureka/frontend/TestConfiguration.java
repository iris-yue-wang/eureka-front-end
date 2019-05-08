package com.zuhlke.eureka.frontend;

import com.zuhlke.eureka.frontend.connectors.Connector;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootConfiguration
@Profile("test")
public class TestConfiguration {

    @Bean
    public Connector testConnector() {
        return () -> {
            throw new RuntimeException();
        };
    }
}