package org.coca.agent.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.coca.agent.core.plugin.intercept.InstanceMethodsInterceptPoint;
import org.coca.agent.core.plugin.match.ClassMatch;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.coca.agent.core.plugin.match.NameMatch.byName;

public class AbstractHttpClientInstrumentation extends HttpClientInstrumentation {
    private static final String ENHANCE_CLASS = "org.apache.http.impl.client.AbstractHttpClient";
    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new InstanceMethodsInterceptPoint(){
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named("doExecute");
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
        return byName(ENHANCE_CLASS);
    }

}
