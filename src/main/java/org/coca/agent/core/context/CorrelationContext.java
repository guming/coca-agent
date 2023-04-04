package org.coca.agent.core.context;

import org.coca.agent.core.util.StringUtil;

import java.util.*;

public class CorrelationContext {
    private final Map<String, String> data = new HashMap<>(3);
    private static final List<String> AUTO_TAG_KEYS = new ArrayList<>();;

    public CorrelationContext() {
    }

    public Optional<String> put(String key, String value) {
        if (key == null) {
            return Optional.empty();
        }

        if (StringUtil.isEmpty(value)) {
            return Optional.ofNullable(data.remove(key));
        }

        if (value.length() > 128) {
            return Optional.empty();
        }

        if (data.containsKey(key)) {
            final String previousValue = data.put(key, value);
            return Optional.of(previousValue);
        }

        if (data.size() >= 3) {
            return Optional.empty();
        }
        data.put(key, value);
        return Optional.empty();
    }
    public Optional<String> get(String key) {
        if (key == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(data.get(key));
    }
}
