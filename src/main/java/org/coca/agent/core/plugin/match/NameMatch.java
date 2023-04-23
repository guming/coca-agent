package org.coca.agent.core.plugin.match;

public class NameMatch implements ClassMatch {
    private String className;

    public NameMatch(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public static NameMatch byName(String className) {
        return new NameMatch(className);
    }
}
