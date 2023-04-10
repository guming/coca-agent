package org.coca.agent.core.plugin;

public class EnhanceException extends PluginException{
    private static final long serialVersionUID = -1L;

    public EnhanceException(String message) {
        super(message);
    }

    public EnhanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
