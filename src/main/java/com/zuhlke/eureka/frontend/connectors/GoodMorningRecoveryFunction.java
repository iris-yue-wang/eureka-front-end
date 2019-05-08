package com.zuhlke.eureka.frontend.connectors;

import io.github.resilience4j.ratpack.recovery.RecoveryFunction;

public class GoodMorningRecoveryFunction implements RecoveryFunction<String> {

    @Override
    public String apply(Throwable throwable) throws Exception {
        return "Recovery";
    }
}
