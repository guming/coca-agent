package org.coca.agent.core.plugin.intercept;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;



public interface ConstructorInterceptPoint {
    ElementMatcher<MethodDescription> getConstructorMatcher();

    String getConstructorIntercepyor();
}
