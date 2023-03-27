package org.coca.agent.core.context;

import java.util.Map;

public interface Span {
    boolean isEntry();
    boolean isExit();
    boolean start();
    Span start(long startTime);
    int getSpanId();
    String getOperationName();
    Span setOperationName(String operationName);
    Span tag(SpanTag yag, String value);
    Span log(Throwable t);
    Span log(long timestamp, Map<String, ?> event);
    Span errorOccurred();
}
