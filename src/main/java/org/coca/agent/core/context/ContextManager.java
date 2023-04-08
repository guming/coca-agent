package org.coca.agent.core.context;

import org.coca.agent.core.boot.BootService;
import org.coca.agent.core.boot.ServiceManager;
import org.coca.agent.core.context.trace.Span;
import org.coca.agent.core.util.StringUtil;

import java.util.Objects;

public class ContextManager implements BootService {
    private static ThreadLocal<TracingContext> CONTEXT = new ThreadLocal<>();
    private static ThreadLocal<RuntimeContext> RUNTIME_CONTEXT = new ThreadLocal<>();

    private static TracingContext getOrCreate(String operationName) {
        TracingContext context = CONTEXT.get();
        if (context == null) {
            context = new TracingContext();
        }
        return context;
    }
    private static TracingContext get() {
        return CONTEXT.get();
    }

    public static int getSpanId() {
        TracingContext context = get();
        return Objects.nonNull(context) ? context.getSpanId() : -1;
    }
    public static String getGlobalTraceId() {
        TracingContext context = get();
        return Objects.nonNull(context) ? context.getTraceId() : "N/A";
    }

    public static String getTraceListId() {
        TracingContext context = get();
        return Objects.nonNull(context) ? context.getTraceListId() : "N/A";
    }
    public static Span createEntrySpan(String operationName, TracingContextCarrier carrier) {
        Span span;
        AbstractTracingContext context;
        operationName = StringUtil.cut(operationName, 100);
        context = getOrCreate(operationName);
        span = context.createEntrySpan(operationName);
        if (carrier != null) {
            context.extract(carrier);
        }
        return span;
    }
    public static Span createExitSpan(String operationName, TracingContextCarrier carrier, String remotePeer) {
        operationName = StringUtil.cut(operationName, 100);
        AbstractTracingContext context = getOrCreate(operationName);
        Span span = context.createExitSpan(operationName, remotePeer);
        context.inject(carrier);
        return span;
    }
    public static Span createExitSpan(String operationName, String remotePeer) {
        operationName = StringUtil.cut(operationName, 100);
        AbstractTracingContext context = getOrCreate(operationName);
        return context.createExitSpan(operationName, remotePeer);
    }
    public static void inject(TracingContextCarrier carrier) {
        get().inject(carrier);
    }

    public static void extract(TracingContextCarrier carrier) {
        if (carrier == null) {
            throw new IllegalArgumentException("ContextCarrier can't be null.");
        }
        get().extract(carrier);
    }

    public static ContextSnapshot capture() {
        return get().capture();
    }
    public static Span activeSpan() {
        return get().activeSpan();
    }
    public static void stopSpan() {
        final AbstractTracingContext context = get();
        stopSpan(context.activeSpan(), context);
    }

    public static void stopSpan(Span span) {
        stopSpan(span, get());
    }

    private static void stopSpan(Span span, final AbstractTracingContext context) {
        if (context.stopSpan(span)) {
            CONTEXT.remove();
            RUNTIME_CONTEXT.remove();
        }
    }

    @Override
    public void prepare() throws Throwable {

    }

    @Override
    public void startup() throws Throwable {

    }

    @Override
    public void onComplete() throws Throwable {

    }

    @Override
    public void shudown() throws Throwable {

    }
}
