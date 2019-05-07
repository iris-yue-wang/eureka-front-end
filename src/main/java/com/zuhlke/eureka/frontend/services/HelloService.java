package com.zuhlke.eureka.frontend.services;

import com.zuhlke.eureka.frontend.connectors.Connector;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class HelloService implements GreetingService {
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);
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
        circuitBreaker.getEventPublisher()
                .onSuccess(event -> logger.info("Call to backend is {}.", event.getEventType().name()))
                .onError(event -> logger.warn("Call to backend is {}. Error: {}.", event.getEventType().name(), event.getThrowable().getMessage()));

        Supplier<String> backendFunction = Retry.decorateSupplier(Retry.ofDefaults("backend"), CircuitBreaker.decorateSupplier(circuitBreaker, connector::getMessage));
        return Try.ofSupplier(backendFunction)
                .recover(t -> recovery())
                .get();
    }

    private String recovery() {
        logger.info("Using recovery.");
        return "Recovery";
    }
}
