package com.zuhlke.eureka.frontend.services;

import com.zuhlke.eureka.frontend.connectors.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("goodMorningService")
public class GoodMorningService implements GreetingService {
    private final Connector connector;

    @Autowired
    public GoodMorningService(@Qualifier("goodMorningConnector") Connector connector) {
        this.connector = connector;
    }

    @Override
    public String getGreetings() {
        return connector.getMessage();
    }

    private String recovery() {
        return "Recovery";
    }
}
