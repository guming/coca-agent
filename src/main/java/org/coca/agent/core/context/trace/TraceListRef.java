package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.ContextSnapshot;
import org.coca.agent.core.context.TracingContextCarrier;

public class TraceListRef {
    private String traceId;
    private int spanId;
    private String traceListId;
    private String parentService;
    private String parentInstance;
    private String parentEndpoint;
    private TraceListRefType type;
    private String addressUsedAtClient;

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

    public TraceListRef(TracingContextCarrier carrier) {
        this.type = TraceListRefType.CROSS_PROCESS;
        this.traceId = carrier.getTraceId();
        this.traceListId = carrier.getTraceListId();
        this.spanId = carrier.getSpanId();
        this.parentService = carrier.getParentService();
        this.parentInstance = carrier.getParentInstance();
        this.parentEndpoint = carrier.getParentEndpoint();
        this.addressUsedAtClient = carrier.getAddressUsedAtClient();
    }
    public TraceListRef(ContextSnapshot snapshot) {
        this.type = TraceListRefType.CROSS_PROCESS;
        this.traceId = snapshot.getTraceId();
        this.traceListId = snapshot.getTraceListId();
        this.spanId = snapshot.getSpanId();
        this.parentService = "";
        this.parentInstance = "";
        this.parentEndpoint = snapshot.getParentEndpoint();
    }

}
