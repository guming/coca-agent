package org.coca.agent.core.plugin.match;

import org.coca.agent.core.plugin.IllegalPluginDefineException;
import org.coca.agent.core.plugin.PluginDefine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public enum PluginLoader {
    INSTANCE;
    private List<PluginDefine> pluginClassList = new ArrayList<PluginDefine>();

    public void load(InputStream inputStream) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String pluginDefineName;
            while ((pluginDefineName = reader.readLine()) != null) {
                if (pluginDefineName.trim().length() == 0 || pluginDefineName.startsWith("#")) {
                    continue;
                }
                try {
                    PluginDefine pluginDefine = PluginDefine.build(pluginDefineName);
                    pluginClassList.add(pluginDefine);
                } catch (IllegalPluginDefineException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            inputStream.close();
        }
    }

    public List<PluginDefine> getPluginClassList() {
        return pluginClassList;
    }
}
