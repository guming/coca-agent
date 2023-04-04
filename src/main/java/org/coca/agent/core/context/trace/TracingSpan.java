package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.*;
import org.coca.agent.core.util.KeyValuePair;
import org.coca.agent.core.util.TagValuePair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class TracingSpan implements Span {
    private final String traceId;
    private final int spanId;
    private final String parentSpanId;
    protected String operationName;
    protected long startTime;
    protected long endTime;
    protected boolean errorOccurred = false;
    protected List<TagValuePair> tags;
    protected List<SpanLogData> logs;
    protected TracingContext tracingContext;

    public TracingSpan(String operationName, String traceId, int spanId, String parentSpanId, TracingContext tracingContext) {
        this.operationName = operationName;
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
        this.tracingContext = tracingContext;
    }

    public void finish(TraceList traceList) {
        endTime = System.currentTimeMillis();

    }

    public String getTraceId() {
        return traceId;
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
    public Span start() {
        this.startTime = System.currentTimeMillis();
        return this;
    }

    @Override
    public int getSpanId() {
        return spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    @Override
    public String getOperationName() {
        return operationName;
    }

    @Override
    public Span setOperationName(String operationName) {
        this.operationName = operationName;
        return this;
    }
    @Override
    public Span tag(String key, String value) {
        return tag(Tags.ofKey(key), value);
    }
    @Override
    public Span tag(SpanTag tag, String value) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(new TagValuePair(tag, value));
        return this;
    }

    @Override
    public Span log(Throwable t) {
        if (logs == null) {
            logs = new LinkedList<>();
        }
        if (!errorOccurred) {
            errorOccurred();
        }
        logs.add(new SpanLogData.Builder().add(new KeyValuePair("event", "error"))
                .add(new KeyValuePair("error.kind", t.getClass().getName()))
                .add(new KeyValuePair("message", t.getMessage()))
                .build(System.currentTimeMillis()));
        return this;
    }


    @Override
    public Span log(long timestampMicroseconds, Map<String, ?> fields) {
        if (logs == null) {
            logs = new LinkedList<>();
        }
        SpanLogData.Builder builder = new SpanLogData.Builder();
        for (Map.Entry<String, ?> entry : fields.entrySet()) {
            builder.add(new KeyValuePair(entry.getKey(), entry.getValue().toString()));
        }
        logs.add(builder.build(timestampMicroseconds));
        return this;
    }

    @Override
    public Span errorOccurred() {
        this.errorOccurred = true;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
