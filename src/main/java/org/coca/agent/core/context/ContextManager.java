package org.coca.agent.core.context;

import org.coca.agent.core.boot.BootService;

public class ContextManager {
    private static ThreadLocal<TracingContext> CONTEXT = new ThreadLocal<>();
    private static ThreadLocal<RuntimeContext> RUNTIME_CONTEXT = new ThreadLocal<>();

    private static TracingContext getContext(String operationName) {
        TracingContext context = CONTEXT.get();
//        if (context == null) {
//            context =
//        }
        return context;
    }
}
