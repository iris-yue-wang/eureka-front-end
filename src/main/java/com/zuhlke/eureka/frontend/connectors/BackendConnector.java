package com.zuhlke.eureka.frontend.connectors;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@CircuitBreaker(name = "backend")
@Service
public class BackendConnector implements Connector {
    private final EurekaClient discoveryClient;

    @Autowired
    public BackendConnector(EurekaClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public String success() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getServiceUrl(), String.class);
    }

    private String getServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("backend", false);
        return instance.getHomePageUrl();
    }
}
