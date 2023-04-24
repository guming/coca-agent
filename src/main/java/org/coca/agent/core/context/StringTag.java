package org.coca.agent.core.context;

import org.coca.agent.core.context.trace.Span;

public class StringTag extends SpanTag<String>{
    public StringTag(int id, String key) {
        super(id, key);
    }

    public StringTag(String key) {
        super(key);
    }

    @Override
    public void set(Span span, String tagValue) {
        span.tag(this, tagValue);
    }
}
