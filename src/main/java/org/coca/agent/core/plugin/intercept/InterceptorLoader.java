package org.coca.agent.core.plugin.intercept;

import org.coca.agent.core.plugin.AgentClassLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class InterceptorLoader {
    private static ConcurrentHashMap<String, Object> INSTANCE_CACHE = new ConcurrentHashMap<String, Object>();
    private static ReentrantLock INSTANCE_LOAD_LOCK = new ReentrantLock();
    private static Map<ClassLoader, ClassLoader> EXTEND_PLUGIN_CLASSLOADERS = new HashMap<ClassLoader, ClassLoader>();

    public static <T> T load(String className, ClassLoader targetClassLoader) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (targetClassLoader == null) {
            targetClassLoader = InterceptorLoader.class.getClassLoader();
        }
        String instanceKey = className + "_OF_" + targetClassLoader.getClass()
                .getName() + "@" + Integer.toHexString(targetClassLoader
                .hashCode());
        Object inst = INSTANCE_CACHE.get(instanceKey);
        if(null == inst){
            INSTANCE_LOAD_LOCK.lock();
            ClassLoader pluginClassLoader;
            try{
                pluginClassLoader = EXTEND_PLUGIN_CLASSLOADERS.get(targetClassLoader);
                if (pluginClassLoader == null) {
                    pluginClassLoader = new AgentClassLoader(targetClassLoader);
                    EXTEND_PLUGIN_CLASSLOADERS.put(targetClassLoader, pluginClassLoader);
                }
            }finally {
                INSTANCE_LOAD_LOCK.unlock();
            }
            inst = Class.forName(className, true, pluginClassLoader).newInstance();
            if (inst != null) {
                INSTANCE_CACHE.put(instanceKey, inst);
            }

        }
        return (T) inst;
    }
}
