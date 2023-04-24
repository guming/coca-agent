package org.coca.agent.core.plugin;

import org.coca.agent.core.plugin.match.PluginLoader;

import java.io.IOException;
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
            try {
                PluginLoader.INSTANCE.load(pluginUrl.openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<PluginDefine> pluginDefineList = PluginLoader.INSTANCE.getPluginClassList();
        List<AbstractClassEnhancePluginDefine> pluginDefines = new ArrayList<>();
        for (PluginDefine pluginDefine: pluginDefineList) {
            try {
                AbstractClassEnhancePluginDefine plugin = (AbstractClassEnhancePluginDefine) Class.forName(pluginDefine.getName(),
                        true, AgentClassLoader.getDefault()).newInstance();
                pluginDefines.add(plugin);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return pluginDefines;
    }
}
