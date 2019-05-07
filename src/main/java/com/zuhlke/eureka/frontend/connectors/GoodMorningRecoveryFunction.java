package com.zuhlke.eureka.frontend.connectors;

import io.github.resilience4j.ratpack.recovery.RecoveryFunction;

public class GoodMorningRecoveryFunction implements RecoveryFunction {
    @Override
    public Object apply(Object o) throws Exception {
        return "Recovery";
    }
}
