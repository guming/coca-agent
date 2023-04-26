package org.coca.agent.core.context;

import org.coca.agent.core.context.trace.TraceList;

public interface TracingContextListener {
    void afterFinished(TraceList traceList);
}
