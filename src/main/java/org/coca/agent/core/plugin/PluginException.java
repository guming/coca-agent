package org.coca.agent.core.plugin;

public class PluginException extends RuntimeException {
    public static final long serialVersionID = -1;

    public PluginException(String message) {
        super(message);
    }

    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }
}
