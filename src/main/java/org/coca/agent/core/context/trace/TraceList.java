package org.coca.agent.core.context.trace;

import org.coca.agent.core.util.IdGenerator;

import java.util.LinkedList;
import java.util.List;

public class TraceList {
    private String traceListId;
    private TraceListRef ref;
    private List<TracingSpan> spanList;
    private final long createTime;
    private String relatedGlobalTraceId;

    public TraceList() {
        this.traceListId = IdGenerator.generate();
        this.spanList = new LinkedList<>();
        this.createTime = System.currentTimeMillis();
    }

    public void ref(TraceListRef traceListRef) {
        if (null == ref) {
            this.ref = traceListRef;
        }
    }

    public void relatedGlobalTrace(String distributedTracedId) {
        this.relatedGlobalTraceId = distributedTracedId;
    }

    public void archive(TracingSpan tracingSpan) {
        spanList.add(tracingSpan);
    }

    public TraceList finish() {
        return this;
    }

    public String getTraceListId() {
        return traceListId;
    }

    public TraceListRef getRef() {
        return ref;
    }

    public List<TracingSpan> getSpanList() {
        return spanList;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getRelatedGlobalTraceId() {
        return relatedGlobalTraceId;
    }
}
