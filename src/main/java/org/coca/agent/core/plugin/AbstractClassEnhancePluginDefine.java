package org.coca.agent.core.plugin;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import org.coca.agent.core.plugin.match.ClassMatch;

public abstract class AbstractClassEnhancePluginDefine {
    public static final String CONTEXT_ATTR_NAME = "_$EnhancedClassField_ws";
    public abstract ConstructorInterceptPoint[] getConstructorsInterceptPoints();
    public abstract InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints();
    public abstract StaticMethodsInterceptPoint[] getStaticMethodsInterceptPoints();
    protected abstract ClassMatch enhanceClass();
    protected abstract DynamicType.Builder<?> enhanceClass(TypeDescription typeDescription, DynamicType.Builder<?> newClassBuilder,
                                                           ClassLoader classLoader) throws PluginException;
    protected abstract DynamicType.Builder<?> enhanceInstance(TypeDescription typeDescription,
                                                              DynamicType.Builder<?> newClassBuilder, ClassLoader classLoader,
                                                              EnhanceContext context) throws PluginException;

    protected DynamicType.Builder<?> enhance(TypeDescription typeDescription, DynamicType.Builder<?> newClassBuilder,
                                             ClassLoader classLoader, EnhanceContext context) throws PluginException {
        newClassBuilder = this.enhanceClass(typeDescription, newClassBuilder, classLoader);

        newClassBuilder = this.enhanceInstance(typeDescription, newClassBuilder, classLoader, context);

        return newClassBuilder;
    }
    public DynamicType.Builder<?> define(TypeDescription typeDescription, DynamicType.Builder<?> builder,
                                         ClassLoader classLoader, EnhanceContext context) throws PluginException {
        String interceptorDefineClassName = this.getClass().getName();
        String transformClassName = typeDescription.getTypeName();

        DynamicType.Builder<?> newClassBuilder = this.enhance(typeDescription, builder, classLoader, context);
        context.initializationStageCompleted();
        return newClassBuilder;
    }
}
