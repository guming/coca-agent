package org.coca.agent.core.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorrelationContext {
    private final Map<String, String> data = new HashMap<>(16);
    private static final List<String> AUTO_TAG_KEYS = new ArrayList<>();;

    public CorrelationContext() {
    }
}
