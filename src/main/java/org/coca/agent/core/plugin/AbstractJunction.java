package org.coca.agent.core.plugin;

import net.bytebuddy.matcher.ElementMatcher;

public class AbstractJunction<V> implements ElementMatcher.Junction<V> {
    @Override
    public <U extends V> Junction<U> and(ElementMatcher<? super U> other) {
        return null;
    }

    @Override
    public <U extends V> Junction<U> or(ElementMatcher<? super U> other) {
        return null;
    }

    @Override
    public boolean matches(V target) {
        return false;
    }
}
