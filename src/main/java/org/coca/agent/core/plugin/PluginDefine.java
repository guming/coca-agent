package org.coca.agent.core.plugin;

public class PluginDefine {
    String name;
    String defineClass;

    public PluginDefine(String name, String defineClass) {
        this.name = name;
        this.defineClass = defineClass;
    }
    public String getDefineClass() {
        return defineClass;
    }

    public void setDefineClass(String defineClass) {
        this.defineClass = defineClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
