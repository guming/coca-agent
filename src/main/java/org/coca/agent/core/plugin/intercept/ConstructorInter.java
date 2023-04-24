package org.coca.agent.core.plugin.intercept;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class ConstructorInter {
    private ConstructorInterceptor interceptor;

    public ConstructorInter(String constructorInterceptorClassName,ClassLoader classLoader) {
        try{
            interceptor = InterceptorLoader.load(constructorInterceptorClassName, classLoader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RuntimeType
    public void intercept(@This Object obj, @AllArguments Object[] allArguments) {
        try {
            interceptor.onConstruct(obj,allArguments);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
