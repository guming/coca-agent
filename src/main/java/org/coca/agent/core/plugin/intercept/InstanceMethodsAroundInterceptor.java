package org.coca.agent.core.plugin.intercept;

import java.lang.reflect.Method;

public interface InstanceMethodsAroundInterceptor {
    void before(Object objInst, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object result) throws Throwable;
    Object after(Object objInst, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object result) throws Throwable;
    void handleException(Object objInst, Method method, Object[] allArguments, Class<?>[] parameterTypes,
                               Throwable t);
}
