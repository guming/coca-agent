package org.coca.agent.core.plugin;

import org.coca.agent.core.plugin.intercept.StaticMethodsInterceptPoint;

public abstract class ClassInstanceMethodsEnhancePluginDefine extends ClassEnhancePluginDefine{
    @Override
    public StaticMethodsInterceptPoint[] getStaticMethodsInterceptPoints() {
        return null;
    }

}
