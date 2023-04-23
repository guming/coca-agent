package org.coca.agent.core.plugin;

/**
 * plugin define from SPI
 */
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

    public static PluginDefine build(String define) throws IllegalPluginDefineException {
        String[] pluginDefine = define.split("=");
        if (pluginDefine.length != 2) {
            throw new IllegalPluginDefineException(define);
        }

        String pluginName = pluginDefine[0];
        String defineClass = pluginDefine[1];
        return new PluginDefine(pluginName, defineClass);
    }
}
