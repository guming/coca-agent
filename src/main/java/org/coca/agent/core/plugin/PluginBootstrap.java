package org.coca.agent.core.plugin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PluginBootstrap {
    public List<AbstractClassEnhancePluginDefine> loadPlugins() {
        AgentClassLoader.initDefaultLoader();
        PluginResolver pluginResolver = new PluginResolver();
        List<URL> resources = pluginResolver.getResources();
        if(resources == null ||resources.size()==0){
            return new ArrayList<>();
        }
        for (URL pluginUrl : resources) {

        }
        return null;
    }
}
