package org.coca.agent.core.context.trace;

import org.coca.agent.core.context.TracingContextCarrier;

public interface ExitTypeSpan {
    String getPeer();

    ExitTypeSpan inject(TracingContextCarrier tracingContextCarrier);
}
