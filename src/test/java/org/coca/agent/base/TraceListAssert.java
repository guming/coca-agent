package org.coca.agent.base;

import org.coca.agent.core.context.trace.TraceListRef;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TraceListAssert {
    public static void assertSegmentId(TraceListRef ref, String segmentId) {
        assertThat(TraceListRefHelper.getTraceSegmentId(ref).toString(), is(segmentId));
    }

    public static void assertSpanId(TraceListRef ref, int spanId) {
        assertThat(TraceListRefHelper.getSpanId(ref), is(spanId));
    }

    public static void assertPeerHost(TraceListRef ref, String peerHost) {
        assertThat(TraceListRefHelper.getPeerHost(ref), is(peerHost));
    }
}
