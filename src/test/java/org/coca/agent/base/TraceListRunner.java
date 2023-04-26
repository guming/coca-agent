package org.coca.agent.base;

import org.coca.agent.core.context.TracingContext;
import org.coca.agent.core.context.TracingContextListener;
import org.coca.agent.core.context.trace.TraceList;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Field;

public class TraceListRunner extends BlockJUnit4ClassRunner {
    private Field field;
    private Object targetObject;
    private TraceListStorage tracingData;
    private TracingContextListener tracingContextListener;
    public TraceListRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        for (Field field : testClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(TraceListStoragePoint.class) && field.getType().equals(TraceListStorage.class)) {
                this.field = field;
                this.field.setAccessible(true);
                break;
            }
        }
    }

    @Override
    protected Object createTest() throws Exception {
        targetObject = super.createTest();
        return targetObject;
    }

    @Override
    protected Statement withAfters(FrameworkMethod method, Object target, Statement statement) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (field != null) {
                    try {
                        tracingData = new TraceListStorage();
                        field.set(targetObject, tracingData);
                    } catch (IllegalAccessException e) {
                    }
                }
                tracingContextListener = new TracingContextListener() {
                    @Override
                    public void afterFinished(TraceList traceSegment) {
                        tracingData.addTraceSegment(traceSegment);
                    }
                };
                TracingContext.ListenerManager.add(tracingContextListener);
                statement.evaluate();
            }
        };
    }
}
