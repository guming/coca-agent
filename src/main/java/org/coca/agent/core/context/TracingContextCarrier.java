package org.coca.agent.core.context;

import org.coca.agent.core.util.Base64;
import org.coca.agent.core.util.StringUtil;

import java.io.Serializable;

public class TracingContextCarrier implements Serializable {
    private String traceId;
    private String traceListId;
    private int spanId=-1;
    private String parentService = "";
    private String parentInstance = "";
    private String parentEndpoint;
    private String addressUsedAtClient;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceListId() {
        return traceListId;
    }

    public void setTraceListId(String traceListId) {
        this.traceListId = traceListId;
    }

    public int getSpanId() {
        return spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }

    public String getParentService() {
        return parentService;
    }

    public void setParentService(String parentService) {
        this.parentService = parentService;
    }

    public String getParentInstance() {
        return parentInstance;
    }

    public void setParentInstance(String parentInstance) {
        this.parentInstance = parentInstance;
    }

    public String getParentEndpoint() {
        return parentEndpoint;
    }

    public void setParentEndpoint(String parentEndpoint) {
        this.parentEndpoint = parentEndpoint;
    }

    public String getAddressUsedAtClient() {
        return addressUsedAtClient;
    }

    public void setAddressUsedAtClient(String addressUsedAtClient) {
        this.addressUsedAtClient = addressUsedAtClient;
    }

    public CarrierItem items() {
        return new CarrierItemHead(new CarrierItem("v1", this.serialize(), null));
    }
    public String serialize() {
        return StringUtil.join(
                '-',
                "1",
                Base64.encode(this.getTraceId()),
                Base64.encode(this.getTraceListId()),
                this.getSpanId() + "",
                Base64.encode(this.getParentService()),
                Base64.encode(this.getParentInstance()),
                Base64.encode(this.getParentEndpoint()),
                Base64.encode(this.getAddressUsedAtClient())
        );
    }
    public TracingContextCarrier deserialize(String text) {
        if (text == null) {
            return this;
        }
        String[] parts = text.split("-", 8);
        if (parts.length == 8) {
            try {
                // parts[0] is sample flag, always trace if header exists.
                this.traceId = Base64.decode2UTFString(parts[1]);
                this.traceListId = Base64.decode2UTFString(parts[2]);
                this.spanId = Integer.parseInt(parts[3]);
                this.parentService = Base64.decode2UTFString(parts[4]);
                this.parentInstance = Base64.decode2UTFString(parts[5]);
                this.parentEndpoint = Base64.decode2UTFString(parts[6]);
                this.addressUsedAtClient = Base64.decode2UTFString(parts[7]);
            } catch (IllegalArgumentException ignored) {

            }
        }
        return this;
    }
}
