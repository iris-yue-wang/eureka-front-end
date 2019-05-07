package com.zuhlke.eureka.frontend.controllers;

import com.zuhlke.eureka.frontend.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodMorningController {

    private final GreetingService greetingService;

    @Autowired
    public GoodMorningController(@Qualifier("goodMorningService") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/goodMorning")
    public String goodMorning() {
        return greetingService.getGreetings();
    }
}
