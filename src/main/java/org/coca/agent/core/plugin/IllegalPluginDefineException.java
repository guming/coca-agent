package org.coca.agent.core.plugin;

public class IllegalPluginDefineException extends Exception {

    public IllegalPluginDefineException(String message) {
        super("illegal plugin define: "+message);
    }
}
