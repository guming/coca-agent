package org.coca.agent.core.context;

public class SpanTag {
    private int id;
    private String key;

    public SpanTag(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public SpanTag(String key) {
        this(-1, key);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
