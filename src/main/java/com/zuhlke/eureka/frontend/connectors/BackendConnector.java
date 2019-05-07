package com.zuhlke.eureka.frontend.connectors;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BackendConnector implements Connector {
    private static final Logger logger = LoggerFactory.getLogger(BackendConnector.class);
    private final EurekaClient discoveryClient;

    @Autowired
    public BackendConnector(EurekaClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public String getMessage() {
        logger.info("Getting message from backend.");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getServiceUrl(), String.class);
    }

    private String getServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("backend", false);
        return instance.getHomePageUrl();
    }
}
