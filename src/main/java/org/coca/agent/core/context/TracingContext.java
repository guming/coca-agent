package org.coca.agent.core.context;

import org.coca.agent.core.context.trace.*;
import org.coca.agent.core.util.IdGenerator;

import java.util.LinkedList;

public class TracingContext implements AbstractTracingContext{
    private TraceList traceList;
    private long createTime;
    private LinkedList<Span> activeSpanStack = new LinkedList<>();
    private int spanIdGenerator = 0;
    private volatile boolean running;
    private Span firstSpan = null;
    private final CorrelationContext correlationContext;
    public TracingContext() {
        this.traceList = new TraceList();
        createTime = System.currentTimeMillis();
        running = true;
        this.correlationContext = new CorrelationContext();
    }

    public void inject(TracingSpan exitSpan, TracingContextCarrier carrier) {
        if (!exitSpan.isExit()) {
            throw new IllegalStateException("Inject can be done only in Exit Span");
        }
        ExitTypeSpan exitTypeSpan = (ExitTypeSpan) exitSpan;
        String peer = exitTypeSpan.getPeer();
        carrier.setTraceId(IdGenerator.generate());
        carrier.setTraceListId(this.traceList.getTraceListId());
        carrier.setSpanId(exitSpan.getSpanId());
        carrier.setParentService("");
        carrier.setParentInstance("");
        carrier.setParentEndpoint(firstSpan.getOperationName());
        carrier.setAddressUsedAtClient(peer);
    }

    @Override
    public void inject(TracingContextCarrier carrier) {
        inject((TracingSpan) activeSpan(),carrier);
    }

    @Override
    public void extract(TracingContextCarrier carrier) {
        TraceListRef ref = new TraceListRef(carrier);
        this.traceList.ref(ref);
        this.traceList.relatedGlobalTrace(carrier.getTraceId());
        Span span = this.activeSpan();
        if (span instanceof EntrySpan) {
            span.ref(ref);
        }
    }

    @Override
    public ContextSnapshot capture() {
        return new ContextSnapshot(traceList.getRelatedGlobalTraceId(),traceList.getTraceListId(),
                activeSpan().getSpanId(), firstSpan.getOperationName(),this.correlationContext);
    }

    @Override
    public String getTraceListId() {
        return traceList.getTraceListId();
    }

    @Override
    public String getTraceId() {
        return traceList.getRelatedGlobalTraceId();
    }

    @Override
    public int getSpanId() {
        return this.activeSpan().getSpanId();
    }

    @Override
    public Span createEntrySpan(String operationName) {
        Span parentSpan = peek();
        int parentSpanId = parentSpan == null ? -1 : parentSpan.getSpanId();
        TracingSpan span = new LocalSpan(operationName, spanIdGenerator++, parentSpanId, this);
        span.start();
        return push(span);
    }

    @Override
    public Span createExitSpan(String operationName, String remotePeer) {
        Span exitSpan;
        Span parentSpan = peek();
        TracingContext owner = this;
        if (parentSpan != null && parentSpan.isExit()) {
            exitSpan = parentSpan;
        } else {
            final int parentSpanId = parentSpan == null ? -1 : parentSpan.getSpanId();
            exitSpan = new ExitSpan(operationName, spanIdGenerator++, parentSpanId , owner, remotePeer);
            push(exitSpan);
        }
        exitSpan.start();
        return exitSpan;
    }

    @Override
    public Span activeSpan() {
        Span span = peek();
        if (span == null) {
            throw new IllegalStateException("No active span.");
        }
        return span;
    }

    @Override
    public boolean stopSpan(Span span) {
        Span lastSpan = peek();
        if (lastSpan == span) {
            if (lastSpan instanceof TracingSpan) {
                TracingSpan toFinishSpan = (TracingSpan) lastSpan;
                if (toFinishSpan.finish(traceList)) {
                    pop();
                }
            } else {
                pop();
            }
        } else {
            throw new IllegalStateException("Stopping the unexpected span = " + span);
        }
        finish();
        return activeSpanStack.isEmpty();
    }
    private Span peek() {
        if (activeSpanStack.isEmpty()) {
            return null;
        }
        return activeSpanStack.getLast();
    }
    private Span pop() {
        return activeSpanStack.removeLast();
    }
    private Span push(Span span) {
        if (firstSpan == null) {
            firstSpan = span;
        }
        activeSpanStack.addLast(span);
        return span;
    }

    private Span first() {
        return firstSpan;
    }
    private void finish() {
        System.out.println("finished");
    }

    @Override
    public void continued(ContextSnapshot snapshot) {
        TraceListRef traceListRef = new TraceListRef(snapshot);
        this.traceList.ref(traceListRef);
        this.activeSpan().ref(traceListRef);
        this.traceList.relatedGlobalTrace(snapshot.getTraceId());
    }
}
