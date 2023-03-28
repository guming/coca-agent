package org.coca.agent.core.context;

import org.coca.agent.core.util.StringUtil;

import java.util.LinkedList;

public class TracingContext {
    private TraceList traceList;
    private long createTime;
    private LinkedList<TracingSpan> activeSpanStack = new LinkedList<>();
    private int spanIdGenerator = 0;
    private volatile boolean running;

    public TracingContext() {
        this.traceList = new TraceList();
        createTime = System.currentTimeMillis();
        running = true;

    }

    public void inject(TracingSpan exitSpan, TracingContextCarrier carrier) {
        if (!exitSpan.isExit()) {
            throw new IllegalStateException("Inject can be done only in Exit Span");
        }
    }

}
