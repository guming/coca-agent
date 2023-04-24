package org.coca.agent.core.plugin.intercept;

import java.lang.reflect.Method;

public interface StaticMethodsAroundInterceptor {
    void before(Class clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object result);
    Object after(Class clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object result);
    void handleException(Class clazz, Method method, Object[] allArguments, Class<?>[] parameterTypes,
                               Throwable t);
}
