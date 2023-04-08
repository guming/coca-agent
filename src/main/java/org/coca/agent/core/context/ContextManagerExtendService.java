package org.coca.agent.core.context;

import org.coca.agent.core.boot.BootService;
import org.coca.agent.core.boot.ServiceManager;

public class ContextManagerExtendService implements BootService {
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

    public TracingContext createTraceContext(String operationName) {
        TracingContext context = null;
        return context;
    }
}
