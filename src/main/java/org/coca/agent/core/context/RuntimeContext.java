package org.coca.agent.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RuntimeContext {
    private final ThreadLocal<RuntimeContext> runtimeContextThreadLocal;
    private Map<Object,Object> context = new ConcurrentHashMap<>(16);
    public RuntimeContext(ThreadLocal<RuntimeContext> runtimeContextThreadLocal) {
        this.runtimeContextThreadLocal = runtimeContextThreadLocal;
    }

    public void put(Object key, Object value) {
        context.put(key, value);
    }
    public Object get(Object key){
        return context.get(key);
    }
    public void remove(Object key) {
        context.remove(key);

        if (context.isEmpty()) {
            runtimeContextThreadLocal.remove();
        }
    }
}
