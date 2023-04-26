package org.coca.agent.base;

import org.coca.agent.core.boot.BootService;
import org.coca.agent.core.boot.ServiceManager;
import org.coca.agent.core.plugin.AgentClassLoader;
import org.junit.rules.ExternalResource;

import java.util.HashMap;

public class AgentServiceRule extends ExternalResource {
    @Override
    protected void after() {
        super.after();
        try {
            FieldSetter.setValue(
                    ServiceManager.INSTANCE.getDeclaringClass(), "bootedServices", new HashMap<Class, BootService>());
            ServiceManager.INSTANCE.shutdown();
        } catch (Exception e) {
        }
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        AgentClassLoader.initDefaultLoader();
        ServiceManager.INSTANCE.boot();
    }
}
