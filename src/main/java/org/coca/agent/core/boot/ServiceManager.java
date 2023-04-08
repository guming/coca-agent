package org.coca.agent.core.boot;

import org.coca.agent.core.plugin.AgentClassLoader;

import java.util.*;

public enum ServiceManager {
    INSTANCE;
    private Map<Class, BootService> bootedServices = Collections.emptyMap();

    public void boot() {
        loadServices();
        prepare();
        startup();
        onComplete();
    }
    public void loadServices() {
        for (BootService bootService : ServiceLoader.load(BootService.class, AgentClassLoader.getDefault())) {
            bootedServices.put(bootService.getClass(), bootService);
        }
    }
    public void shutdown() {
        bootedServices.values().stream().sorted(Comparator.comparingInt(BootService::priority).reversed()).forEach(bootService -> {
            try {
                bootService.shudown();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    public <T extends BootService> T findService(Class<T> serviceClass) {
        return (T) bootedServices.get(serviceClass);
    }

    private void prepare() {
        bootedServices.values().stream().sorted(Comparator.comparingInt(BootService::priority)).forEach(service -> {
            try {
                service.prepare();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void startup() {
        bootedServices.values().stream().sorted(Comparator.comparingInt(BootService::priority)).forEach(service -> {
            try {
                service.startup();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void onComplete() {
        bootedServices.values().stream().sorted(Comparator.comparingInt(BootService::priority)).forEach(service -> {
            try {
                service.onComplete();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
}
