package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.TracingContextCarrier;

public class NoopExitSpan extends NoopSpan implements ExitTypeSpan{
    private String peer;

    public NoopExitSpan(String peer) {
        this.peer = peer;
    }

    @Override
    public String getPeer() {
        return peer;
    }

    @Override
    public ExitTypeSpan inject(TracingContextCarrier tracingContextCarrier) {
        return this;
    }

    @Override
    public boolean isExit() {
        return true;
    }

}
