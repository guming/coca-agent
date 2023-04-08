package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.TracingContext;

public class EntrySpan extends StackBasedTracingSpan {
    private int currentMaxDepth;

    public EntrySpan(String operationName, int spanId, int parentSpanId, TracingContext tracingContext) {
        super(operationName, spanId, parentSpanId, tracingContext);
        this.currentMaxDepth = 0;
    }

    @Override
    public boolean isEntry() {
        return true;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public Span start() {
        if ((currentMaxDepth = ++stackDepth) == 1) {
            super.start();
        }
        return this;
    }

    @Override
    public Span setOperationName(String operationName) {
        return super.setOperationName(operationName);
    }

    @Override
    public Span tag(String key, String value) {
        if (stackDepth == currentMaxDepth) {
            super.tag(key, value);
        }
        return this;
    }

    @Override
    public Span log(Throwable t) {
        super.log(t);
        return this;
    }

}
