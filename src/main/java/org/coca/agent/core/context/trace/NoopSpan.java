package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.SpanTag;

import java.util.Map;

public class NoopSpan implements Span {
    @Override
    public boolean isEntry() {
        return false;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public Span start() {
        return this;
    }

    @Override
    public int getSpanId() {
        return 0;
    }

    @Override
    public String getOperationName() {
        return "";
    }

    @Override
    public Span setOperationName(String operationName) {
        return this;
    }

    @Override
    public Span tag(SpanTag yag, String value) {
        return this;
    }

    @Override
    public Span log(Throwable t) {
        return this;
    }

    @Override
    public Span log(long timestamp, Map<String, ?> event) {
        return this;
    }

    @Override
    public Span errorOccurred() {
        return this;
    }

    @Override
    public Span setPeer(String remotePeer) {
        return this;
    }

    @Override
    public void ref(TraceListRef ref) {

    }

    @Override
    public Span tag(String key, String value) {
        return this;
    }

    public void finish() {
    }
}
