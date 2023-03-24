package org.coca.agent.core.context;

import java.util.UUID;

public class TracingSpan {
    private final String traceId;
    private final String spanId;
    private final String parentSpanId;
    private String operationName;
    private final long startTime;
    private long endTime;

    public TracingSpan(String operationName) {
        this(operationName, UUID.randomUUID().toString(), UUID.randomUUID().toString(), null, System.currentTimeMillis());
    }

    public TracingSpan(String operationName, String traceId, String spanId, String parentSpanId, long startTime) {
        this.operationName = operationName;
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
        this.startTime = startTime;
    }

    public void finish() {
        endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("SpanTracing finished for operation " + operationName + " with duration " + duration + "ms.");
    }
}
