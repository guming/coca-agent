package org.coca.agent.core.context;

import org.coca.agent.core.util.KeyValuePair;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SpanLogData {
    private long timestamp;
    private List<KeyValuePair> logs;

    public SpanLogData(long timestamp, List<KeyValuePair> logs) {
        this.timestamp = timestamp;
        this.logs = logs;
    }

    public List<KeyValuePair> getLogs() {
        return logs;
    }
    public static class Builder{
        protected  List<KeyValuePair> logs;

        public Builder() {
            this.logs = new LinkedList<>();
        }
        public Builder add(KeyValuePair... fields){
            Collections.addAll(logs, fields);
            return this;
        }
        public SpanLogData build(long timestamp){
            return new SpanLogData(timestamp, logs);
        }
    }

    @Override
    public String toString() {
        return "SpanLogData{" +
                "timestamp=" + timestamp +
                ", logs=" + logs +
                '}';
    }
}
