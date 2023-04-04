package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.TracingContext;

public class LocalSpan extends TracingSpan {

    public LocalSpan(String operationName, String traceId, int spanId, String parentSpanId, TracingContext tracingContext) {
        super(operationName, traceId, spanId, parentSpanId, tracingContext);
    }

    @Override
    public boolean isEntry() {
        return false;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public Span setPeer(String remotePeer) {
        return this;
    }

}
