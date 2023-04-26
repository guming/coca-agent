package org.coca.agent.base;

import org.coca.agent.core.context.trace.TraceList;
import org.coca.agent.core.context.trace.TracingSpan;

import java.util.List;

public class TraceListHelper {
    public static List<TracingSpan> getSpans(TraceList traceList) {
        try {
            return FieldGetter.getValue(traceList, "spanList");
        } catch (Exception e) {
        }

        return null;
    }
}
