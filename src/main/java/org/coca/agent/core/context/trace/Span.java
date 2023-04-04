package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.SpanTag;

import java.util.Map;

public interface Span {
    boolean isEntry();
    boolean isExit();
    Span start();
    int getSpanId();
    String getOperationName();
    Span setOperationName(String operationName);
    Span tag(SpanTag yag, String value);

    Span tag(String key, String value);
    Span log(Throwable t);
    Span log(long timestamp, Map<String, ?> event);
    Span errorOccurred();
    Span setPeer(String remotePeer);
}
