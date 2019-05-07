package com.zuhlke.eureka.frontend.services;

import com.zuhlke.eureka.frontend.connectors.Connector;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class HelloService implements GreetingService {
    private final Connector connector;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    public HelloService(Connector connector, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.connector = connector;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Override
    public String getGreetings() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backend");
        Supplier<String> backendFunction = CircuitBreaker.decorateSupplier(circuitBreaker, connector::success);
        return Try.ofSupplier(backendFunction)
                .recover(t -> recovery())
                .get();
    }

    private String recovery() {
        return "Recovery";
    }
}
