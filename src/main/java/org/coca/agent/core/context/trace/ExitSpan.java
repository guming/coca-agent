package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.TracingContext;
import org.coca.agent.core.context.TracingContextCarrier;

public class ExitSpan extends StackBasedTracingSpan implements ExitTypeSpan {
    public ExitSpan(String operationName, int spanId, int parentSpanId, TracingContext tracingContext) {
        super(operationName, spanId, parentSpanId, tracingContext);
    }

    public ExitSpan(String operationName, int spanId, int parentSpanId, TracingContext tracingContext, String peer) {
        super(operationName, spanId, parentSpanId, tracingContext, peer);
    }
    public ExitSpan start() {
        if (++stackDepth == 1) {
            super.start();
        }
        return this;
    }

    @Override
    public boolean isEntry() {
        return false;
    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public String getPeer() {
        return peer;
    }

    @Override
    public ExitTypeSpan inject(TracingContextCarrier tracingContextCarrier) {
        this.tracingContext.inject(this,tracingContextCarrier);
        return this;
    }

    @Override
    public ExitSpan log(Throwable t) {
        if (stackDepth == 1) {
            super.log(t);
        }
        return this;
    }

    @Override
    public ExitSpan tag(String key, String value) {
        super.tag(key, value);
        return this;
    }
    @Override
    public Span setOperationName(String operationName) {
        if (stackDepth == 1) {
            return super.setOperationName(operationName);
        } else {
            return this;
        }
    }

}
