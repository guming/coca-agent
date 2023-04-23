package org.coca.agent.core.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PluginResolver {
    public List<URL> getResources(){
        List<URL> urls = new ArrayList<URL>();
        Enumeration<URL> resources = null;
        try {
            resources = AgentClassLoader.getDefault().getResources("coca-plugin.def");
            while(resources.hasMoreElements()){
                URL url = resources.nextElement();
                urls.add(url);
            }
            return urls;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
