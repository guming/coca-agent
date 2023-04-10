package org.coca.agent.core.plugin;

public class EnhanceContext {
    private boolean isEnhanced = false;
    private boolean objectExtended = false;
    public void initializationStageCompleted() {
        isEnhanced = true;
    }
    public boolean isEnhanced() {
        return isEnhanced;
    }
    public boolean isObjectExtended() {
        return isObjectExtended();
    }
    public void extendObjectCompleted() {
        objectExtended = true;
    }
}
