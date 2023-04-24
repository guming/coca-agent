package org.coca.agent.core.plugin.intercept;

import net.bytebuddy.implementation.bind.annotation.*;
import org.coca.agent.core.plugin.PluginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InstanceMethodsInter {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceMethodsInter.class);
    private InstanceMethodsAroundInterceptor interceptor;
    public InstanceMethodsInter(String instanceMethodsAroundInterceptorClassName, ClassLoader classLoader) {
        try {
            interceptor = InterceptorLoader.load(instanceMethodsAroundInterceptorClassName, classLoader);
        } catch (Throwable t) {
            throw new PluginException("Can't create InstanceMethodsAroundInterceptor.", t);
        }
    }
    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> zuper,
                            @Origin Method method) throws Throwable {
        Object result = null;
        try {
            interceptor.before(obj, method, allArguments, method.getParameterTypes(),result);
            result = zuper.call();
        } catch (Exception e) {
            try {
                interceptor.handleException(obj, method, allArguments, method.getParameterTypes(), e);
            }catch(Exception e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try{
                interceptor.after(obj, method, allArguments, method.getParameterTypes(), result);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
