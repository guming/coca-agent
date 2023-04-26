package org.coca.agent.base;

import org.coca.agent.core.context.trace.TraceListRef;

public class TraceListRefHelper {
    public static String getPeerHost(TraceListRef ref) {
        try {
            return FieldGetter.getValue(ref, "addressUsedAtClient");
        } catch (Exception e) {
        }

        return null;
    }

    public static String getTraceSegmentId(TraceListRef ref) {
        try {
            return FieldGetter.getValue(ref, "traceId");
        } catch (Exception e) {
        }

        return null;
    }

    public static int getSpanId(TraceListRef ref) {
        try {
            return FieldGetter.getValue(ref, "spanId");
        } catch (Exception e) {
        }

        return -1;
    }

    public static String getParentServiceInstance(TraceListRef ref) {
        try {
            return FieldGetter.getValue(ref, "parentInstance");
        } catch (Exception e) {
        }

        return "Unknown";
    }
}
