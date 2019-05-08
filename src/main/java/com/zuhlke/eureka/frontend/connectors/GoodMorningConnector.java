package com.zuhlke.eureka.frontend.connectors;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Retry(name = "goodMorning", recovery = GoodMorningRecoveryFunction.class)
@CircuitBreaker(name = "goodMorning")
@Service("goodMorningConnector")
public class GoodMorningConnector implements Connector {
    private static final Logger logger = LoggerFactory.getLogger(GoodMorningConnector.class);
    private final EurekaClient discoveryClient;

    @Autowired
    public GoodMorningConnector(EurekaClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    @Retry(name = "goodMorning")
    public String getMessage() {
        logger.info("Getting good morning from backend.");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getServiceUrl(), String.class);
    }

    private String getServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("backend", false);
        return instance.getHomePageUrl() + "/goodMorning";
    }
}
