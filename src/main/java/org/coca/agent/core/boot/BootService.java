package org.coca.agent.core.boot;


public interface BootService {
    void prepare() throws Throwable;
    void startup() throws Throwable;
    void onComplete() throws Throwable;
    void shudown() throws Throwable;
    default int priority() {
        return 0;
    }
}
