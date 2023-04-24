package org.coca.agent.core.plugin.intercept;

public interface ConstructorInterceptor {
    void onConstruct(Object objInst, Object[] allArguments) throws Throwable;
}
