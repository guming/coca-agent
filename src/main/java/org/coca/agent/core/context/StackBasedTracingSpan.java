package org.coca.agent.core.context;

public class StackBasedTracingSpan extends TracingSpan{
    private int stackDepth;
    private String peer;

    public StackBasedTracingSpan(String operationName, String traceId, int spanId, String parentSpanId, long startTime) {
        super(operationName, traceId, spanId, parentSpanId, startTime);
    }

    public int getStackDepth() {
        return stackDepth;
    }

    public void setStackDepth(int stackDepth) {
        this.stackDepth = stackDepth;
    }

    public String getPeer() {
        return peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }
}
