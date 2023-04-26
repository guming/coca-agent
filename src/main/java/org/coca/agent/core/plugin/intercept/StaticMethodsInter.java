package org.coca.agent.core.plugin.intercept;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class StaticMethodsInter {
    public String className;

    public StaticMethodsInter(String className) {
        this.className = className;
    }
    @RuntimeType
    public Object intercept(@Origin Class<?> clazz, @AllArguments Object[] allArguments,
                            @SuperCall Callable<?> zuper, @Origin Method method) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        StaticMethodsAroundInterceptor interceptor = InterceptorLoader.load(className, clazz.getClassLoader());
        Object result = null;
        try {
            interceptor.before(clazz, method, allArguments, method.getParameterTypes(),result);
            result = zuper.call();
        } catch (Exception e) {
            try {
                interceptor.handleException(clazz, method, allArguments, method.getParameterTypes(), e);
            }catch(Exception e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
              try{
                  interceptor.after(clazz, method, allArguments, method.getParameterTypes(), result);
              }catch(Exception e){
                  e.printStackTrace();
              }
        }
        return result;
    }
}
