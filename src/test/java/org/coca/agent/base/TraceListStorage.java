package org.coca.agent.base;

import org.coca.agent.core.context.trace.TraceList;
import org.coca.agent.core.context.trace.TraceListRef;

import java.util.ArrayList;
import java.util.List;

public class TraceListStorage {
    private List<TraceList> traceList;

    public TraceListStorage() {
        this.traceList = new ArrayList<TraceList>();
    }

    public List<TraceList> getTraceList() {
        return traceList;
    }

    public void setTraceList(List<TraceList> traceList) {
        this.traceList = traceList;
    }

    void addTraceSegment(TraceList segment) {
        traceList.add(segment);
    }
}
