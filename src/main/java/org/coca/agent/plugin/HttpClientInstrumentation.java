package org.coca.agent.plugin;

import org.coca.agent.core.plugin.ClassInstanceMethodsEnhancePluginDefine;
import org.coca.agent.core.plugin.intercept.ConstructorInterceptPoint;
import org.coca.agent.core.plugin.intercept.InstanceMethodsInterceptPoint;
import org.coca.agent.core.plugin.intercept.StaticMethodsInterceptPoint;

public abstract class HttpClientInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    private static final String INTERCEPT_CLASS = "";
    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints(){
        return null;
    }
    public String getInstanceMethodsInterceptor(){
        return INTERCEPT_CLASS;
    }
}
