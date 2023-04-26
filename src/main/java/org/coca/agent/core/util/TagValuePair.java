package org.coca.agent.core.util;

import org.coca.agent.core.context.SpanTag;

public class TagValuePair {
    private SpanTag key;
    private String value;

    public TagValuePair(SpanTag key, String value) {
        this.key = key;
        this.value = value;
    }

    public SpanTag getKey() {
        return key;
    }

    public void setKey(SpanTag key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TagValuePair{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
