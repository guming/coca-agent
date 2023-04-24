package org.coca.agent.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.coca.agent.core.plugin.intercept.InstanceMethodsInterceptPoint;
import org.coca.agent.core.plugin.match.ClassMatch;
import org.coca.agent.core.plugin.match.NameMatch;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class DefaultRequestDirectorInstrumentation extends HttpClientInstrumentation {
    private static final String ENHANCE_CLASS = "org.apache.http.impl.client.DefaultRequestDirector";
    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named("execute");
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return getInstanceMethodsInterceptor();
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }

    @Override
    protected ClassMatch enhanceClass() {
        return NameMatch.byName(ENHANCE_CLASS);
    }

}
