package org.coca.agent.core.context;

import org.coca.agent.core.context.trace.Span;

public interface AbstractTracingContext {
    void inject(TracingContextCarrier carrier);

    void extract(TracingContextCarrier carrier);

    ContextSnapshot capture();

    String getTraceListId();

    String getTraceId();

    int getSpanId();

    Span createEntrySpan(String operationName);
    Span createExitSpan(String operationName, String remotePeer);
    Span activeSpan();

    boolean stopSpan(Span span);

}
