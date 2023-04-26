package org.coca.agent.base;

import org.coca.agent.core.context.SpanLogData;
import org.coca.agent.core.context.trace.Span;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpanAssert {
    public static void assertLogSize(Span span, int exceptedSize) {
        assertThat(SpanHelper.getLogs(span).size(), is(exceptedSize));
    }

    public static void assertTagSize(Span span, int exceptedSize) {
        assertThat(SpanHelper.getTags(span).size(), is(exceptedSize));
    }

    public static void assertException(SpanLogData logDataEntity, Class<? extends Throwable> throwableClass,
                                       String message) {
        Assert.assertThat(logDataEntity.getLogs().size(), is(4));
        Assert.assertThat(logDataEntity.getLogs().get(0).getValue(), CoreMatchers.<Object>is("error"));
        Assert.assertThat(logDataEntity.getLogs().get(1).getValue(), CoreMatchers.<Object>is(throwableClass.getName()));
        Assert.assertThat(logDataEntity.getLogs().get(2).getValue(), is(message));
        assertNotNull(logDataEntity.getLogs().get(3).getValue());
    }

    public static void assertException(SpanLogData logDataEntity, Class<? extends Throwable> throwableClass) {
        Assert.assertThat(logDataEntity.getLogs().size(), is(4));
        Assert.assertThat(logDataEntity.getLogs().get(0).getValue(), CoreMatchers.<Object>is("error"));
        Assert.assertThat(logDataEntity.getLogs().get(1).getValue(), CoreMatchers.<Object>is(throwableClass.getName()));
        Assert.assertNull(logDataEntity.getLogs().get(2).getValue());
        assertNotNull(logDataEntity.getLogs().get(3).getValue());
    }

//    public static void assertComponent(Span span, Component component) {
//        assertThat(SpanHelper.getComponentId(span), is(component.getId()));
//    }

//    public static void assertComponent(Span span, String componentName) {
//        assertThat(SpanHelper.getComponentName(span), is(componentName));
//    }

//    public static void assertLayer(Span span, SpanLayer spanLayer) {
//        assertThat(SpanHelper.getLayer(span), is(spanLayer));
//    }

    public static void assertTag(Span span, int index, String value) {
        assertThat(SpanHelper.getTags(span).get(index).getValue(), is(value));
    }

    public static void assertOccurException(Span span, boolean excepted) {
        assertThat(SpanHelper.getErrorOccurred(span), is(excepted));
    }
}
