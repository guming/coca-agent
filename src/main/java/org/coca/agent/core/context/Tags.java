package org.coca.agent.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Tags {

    private static final Map<String, SpanTag> TAG_PROTOTYPES = new ConcurrentHashMap<>();

    private Tags() {
    }
    public static  SpanTag ofKey(String key){
        return TAG_PROTOTYPES.computeIfAbsent(key, SpanTag::new);
    }

}
