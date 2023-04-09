package org.coca.agent.core.plugin;

import java.util.concurrent.locks.ReentrantLock;

public class AgentClassLoader extends ClassLoader {
    private static AgentClassLoader DEFAULT_LOADER;
    private ReentrantLock jarLoadLock = new ReentrantLock();

    public static void initDefaultLoader() {
        if (DEFAULT_LOADER == null) {
            synchronized (AgentClassLoader.class) {
                if (DEFAULT_LOADER == null) {
                    DEFAULT_LOADER = new AgentClassLoader(PluginBootstrap.class.getClassLoader());
                }
            }
        }
    }

    public static AgentClassLoader getDefault() {
        return DEFAULT_LOADER;
    }

    protected AgentClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
