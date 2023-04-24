package org.coca.agent.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Tags {

    private static final Map<String, SpanTag> TAG_PROTOTYPES = new ConcurrentHashMap<>();

    private Tags() {
    }
    public static  SpanTag ofKey(String key){
        return TAG_PROTOTYPES.computeIfAbsent(key, StringTag::new);
    }

    public static final StringTag URL = new StringTag(1, "url");
    public static final class HTTP {
        public static final StringTag METHOD = new StringTag(10, "http.method");

        public static final StringTag PARAMS = new StringTag(11, "http.params");

        public static final StringTag BODY = new StringTag(13, "http.body");

        public static final StringTag HEADERS = new StringTag(14, "http.headers");
    }
}
