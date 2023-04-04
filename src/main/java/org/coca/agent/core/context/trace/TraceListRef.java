package org.coca.agent.core.context.trace;

public class TraceListRef {
    private String traceId;
    private int spanId;
    private String traceListId;
    private String parentService;
    private String parentInstance;
    private String parentEndpoint;
    private TraceListRefType type;

    public TraceListRefType getType() {
        return type;
    }

    public String getTraceId() {
        return traceId;
    }

    public int getSpanId() {
        return spanId;
    }

    public String getTraceListId() {
        return traceListId;
    }

    public String getParentService() {
        return parentService;
    }

    public String getParentInstance() {
        return parentInstance;
    }

    public String getParentEndpoint() {
        return parentEndpoint;
    }
    public enum TraceListRefType {
        CROSS_PROCESS, CROSS_THREAD
    }
}
