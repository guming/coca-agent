package org.coca.agent.core.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface InstanceMethodsInterceptPoint {
    ElementMatcher<MethodDescription> getMethodsMatcher();
    String getMethodsInterceptor();
    boolean isOverrideArgs();
}
