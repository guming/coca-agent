package org.coca.agent.core.context.listener;

import org.coca.agent.core.boot.BootService;
import org.coca.agent.core.context.TracingContext;
import org.coca.agent.core.context.TracingContextListener;
import org.coca.agent.core.context.trace.TraceList;

public class TraceListConsolePrintService implements BootService, TracingContextListener {
    @Override
    public void prepare() throws Throwable {

    }

    @Override
    public void startup() throws Throwable {

    }

    @Override
    public void onComplete() throws Throwable {
        TracingContext.ListenerManager.add(this);
    }

    @Override
    public void shudown() throws Throwable {

    }

    @Override
    public void afterFinished(TraceList traceList) {
        traceList.print();
        System.out.println("listener finished.");
    }
}
