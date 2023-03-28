package org.coca.agent.core.context;

import java.util.Iterator;

public class CarrierItem implements Iterator<CarrierItem> {
    private String headKey;
    private String headValue;
    private CarrierItem next;

    public CarrierItem(String headKey, String headValue) {
        this(headKey, headValue, null);
    }

    public CarrierItem(String headKey, String headValue, CarrierItem next) {
        this.headKey = headKey;
        this.headValue = headValue;
        this.next = next;
    }

    public String getHeadKey() {
        return headKey;
    }

    public void setHeadKey(String headKey) {
        this.headKey = headKey;
    }

    public String getHeadValue() {
        return headValue;
    }

    public void setHeadValue(String headValue) {
        this.headValue = headValue;
    }

    @Override
    public boolean hasNext() {
        return next!=null;
    }

    @Override
    public CarrierItem next() {
        return next;
    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }
}
