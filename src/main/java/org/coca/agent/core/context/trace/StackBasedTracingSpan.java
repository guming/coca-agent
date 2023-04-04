package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.TracingContext;

public class StackBasedTracingSpan extends TracingSpan {
    protected int stackDepth;
    protected String peer;

    public StackBasedTracingSpan(String operationName, String traceId, int spanId, String parentSpanId,
                                 TracingContext tracingContext) {
        super(operationName, traceId, spanId, parentSpanId, tracingContext);
        this.stackDepth = 0;
        this.peer = null;
    }

    public StackBasedTracingSpan(String operationName, String traceId, int spanId, String parentSpanId,
                                 TracingContext tracingContext, String peer) {
        super(operationName, traceId, spanId, parentSpanId, tracingContext);
        this.stackDepth = 0;
        this.peer = peer;
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

    public Span setPeer(String peer) {
        this.peer = peer;
        return this;
    }

    @Override
    public void finish(TraceList traceList) {
        this.endTime = System.currentTimeMillis();
        super.finish(traceList);
    }
}
