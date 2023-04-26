package org.coca.agent.plugin;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.coca.agent.core.context.CarrierItem;
import org.coca.agent.core.context.ContextManager;
import org.coca.agent.core.context.Tags;
import org.coca.agent.core.context.TracingContextCarrier;
import org.coca.agent.core.context.trace.Span;
import org.coca.agent.core.plugin.intercept.InstanceMethodsAroundInterceptor;
import org.coca.agent.core.util.StringUtil;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class HttpClientExecuteInterceptor implements InstanceMethodsAroundInterceptor {
    @Override
    public void before(Object objInst, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object result) throws Throwable{
        final HttpHost httpHost = (HttpHost) allArguments[0];
        HttpRequest httpRequest = (HttpRequest) allArguments[1];
        final TracingContextCarrier contextCarrier = new TracingContextCarrier();

        String remotePeer = httpHost.getHostName() + ":" + port(httpHost);

        String uri = httpRequest.getRequestLine().getUri();
        String requestURI = getRequestURI(uri);
        String operationName = requestURI;
        Span span = ContextManager.createExitSpan(operationName, contextCarrier, remotePeer);

//        span.setComponent(ComponentsDefine.HTTPCLIENT);
        Tags.URL.set(span, buildSpanValue(httpHost, uri));
        Tags.HTTP.METHOD.set(span, httpRequest.getRequestLine().getMethod());
//        SpanLayer.asHttp(span);

        CarrierItem next = contextCarrier.items();
        while (next.hasNext()) {
            next = next.next();
            httpRequest.setHeader(next.getHeadKey(), next.getHeadValue());
        }
        collectHttpParam(httpRequest, span);
    }

    @Override
    public Object after(Object objInst, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object ret) {
        if (ret != null) {
            HttpResponse response = (HttpResponse) ret;
            StatusLine responseStatusLine = response.getStatusLine();
            if (responseStatusLine != null) {
                int statusCode = responseStatusLine.getStatusCode();
                Span span = ContextManager.activeSpan();
                if (statusCode >= 400) {
                    span.errorOccurred();
                    Tags.STATUS_CODE.set(span, Integer.toString(statusCode));
                }
                HttpRequest httpRequest = (HttpRequest) allArguments[1];
            }
        }

        ContextManager.stopSpan();
        return ret;
    }

    @Override
    public void handleException(Object objInst, Method method, Object[] allArguments, Class<?>[] parameterTypes, Throwable t) {
        Span activeSpan = ContextManager.activeSpan();
        activeSpan.log(t);
    }
    private String getRequestURI(String uri) throws MalformedURLException {
        if (isUrl(uri)) {
            String requestPath = new URL(uri).getPath();
            return requestPath != null && requestPath.length() > 0 ? requestPath : "/";
        } else {
            return uri;
        }
    }

    private boolean isUrl(String uri) {
        String lowerUrl = uri.toLowerCase();
        return lowerUrl.startsWith("http") || lowerUrl.startsWith("https");
    }

    private String buildSpanValue(HttpHost httpHost, String uri) {
        if (isUrl(uri)) {
            return uri;
        } else {
            StringBuilder buff = new StringBuilder();
            buff.append(httpHost.getSchemeName().toLowerCase());
            buff.append("://");
            buff.append(httpHost.getHostName());
            buff.append(":");
            buff.append(port(httpHost));
            buff.append(uri);
            return buff.toString();
        }
    }

    private int port(HttpHost httpHost) {
        int port = httpHost.getPort();
        return port > 0 ? port : "https".equals(httpHost.getSchemeName().toLowerCase()) ? 443 : 80;
    }

    private void collectHttpParam(HttpRequest httpRequest, Span span) {
        if (httpRequest instanceof HttpUriRequest) {
            URI uri = ((HttpUriRequest) httpRequest).getURI();
            String tagValue = uri.getQuery();
            if (StringUtil.isNotEmpty(tagValue)) {
                Tags.HTTP.PARAMS.set(span, tagValue);
            }
        }
    }
}
