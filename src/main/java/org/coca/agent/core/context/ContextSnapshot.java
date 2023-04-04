package org.coca.agent.core.context;

public class ContextSnapshot {
    private String traceId;
    private String traceListId;
    private int spanId;
    private String parentEndpoint;
    private CorrelationContext correlationContext;

    public ContextSnapshot(String traceId, String traceListId, int spanId, String parentEndpoint, CorrelationContext correlationContext) {
        this.traceId = traceId;
        this.traceListId = traceListId;
        this.spanId = spanId;
        this.parentEndpoint = parentEndpoint;
        this.correlationContext = correlationContext;
    }

    public CorrelationContext getCorrelationContext() {
        return correlationContext;
    }
    public boolean isValid() {
        return traceListId != null && spanId > -1 && traceId != null;
    }
}
