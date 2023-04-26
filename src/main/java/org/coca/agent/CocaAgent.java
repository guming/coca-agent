package org.coca.agent;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassInjector;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.coca.agent.core.boot.ServiceManager;
import org.coca.agent.core.plugin.*;
import org.coca.agent.sample.DemoService;
import org.coca.agent.plugin.SimpleInstanceInterceptor;
import org.coca.agent.sample.ExampleHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;


/**
 * Agent bootstrap
 */
public class CocaAgent {

    public static final Logger LOGGER = LoggerFactory.getLogger(CocaAgent.class);

    public static void main(String[] args) {
        try {
            premain(null,ByteBuddyAgent.install());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExampleHttp.getClient();
    }
    public static void premain(String arguments, Instrumentation instrumentation) throws IOException {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Agent starting...");
        }
        PluginFinder pluginFinder = new PluginFinder(new PluginBootstrap().loadPlugins());

        AgentBuilder agentBuilder =new AgentBuilder.Default()
                .ignore(nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("javassist"))
                        .or(nameStartsWith("asm"))
                        .or(nameStartsWith("sun.reflect"))
                        .or(nameStartsWith("org.coca.agent"))
                        .or(ElementMatchers.isSynthetic()));

        //adopt for jdk11+
        //using unsafe injection with bootstrap classloader
        Map<String, byte[]> classesTypeMap = new HashMap<>();
        classesTypeMap.put(new TypeDescription.ForLoadedType(DelegateTemplate.class).getName(),
                ClassFileLocator.ForClassLoader.read(DelegateTemplate.class));
        ClassInjector.UsingUnsafe.Factory factory = ClassInjector.UsingUnsafe.Factory.resolve(instrumentation);
        factory.make(null, null).injectRaw(classesTypeMap);
        agentBuilder = agentBuilder.with(new AgentBuilder.InjectionStrategy.UsingUnsafe.OfFactory(factory));

        //retransformation for plugins
        agentBuilder.type(pluginFinder.buildMatch())
                .transform(new Transformer(pluginFinder))
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .with(new Listener())
                .installOn(instrumentation);
        //service boot
        ServiceManager.INSTANCE.boot();
        //shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(ServiceManager.INSTANCE::shutdown));
    }

    private static class Transformer implements AgentBuilder.Transformer {
        private PluginFinder pluginFinder;
        public Transformer(PluginFinder pluginFinder) {
            this.pluginFinder = pluginFinder;
        }

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
            List<AbstractClassEnhancePluginDefine> pluginDefines = pluginFinder.find(typeDescription);
            if(pluginDefines.size()>0){
                DynamicType.Builder<?> newBuilder = builder;
                EnhanceContext context = new EnhanceContext();
                for (AbstractClassEnhancePluginDefine pluginDefine:  pluginDefines) {
                    DynamicType.Builder<?> tempBuilder=pluginDefine.define(typeDescription, builder, classLoader, context);
                    if(tempBuilder!=null){
                        newBuilder = tempBuilder;
                    }
                    if(context.isEnhanced()){
                        LOGGER.debug("finish the prepare stage for {}", typeDescription.getName());
                    }
                }
                return newBuilder;
            }
            return builder;
        }
    }
    private static class Listener implements AgentBuilder.Listener{
        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }

        @Override
        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
            LOGGER.info("On Transformation class {}.", typeDescription.getName());
        }

        @Override
        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }

        @Override
        public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
            LOGGER.error("Enhance class " + typeName + " error.", throwable);
        }

        @Override
        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }
    }
}
