package projetostcc.core.security.cas;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerClientConfiguration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;

public class BasicAuthenticationHttpInvokerProxyFactoryBean extends HttpInvokerProxyFactoryBean {

    public BasicAuthenticationHttpInvokerProxyFactoryBean(String username, String password, String serviceUrl) throws ClientProtocolException {
        Assert.notNull(username, "username could not be NULL");
        Assert.notNull(password, "password could not be NULL");
        Assert.notNull(serviceUrl, "serviceUrl could not be NULL");

        setHttpInvokerRequestExecutor(
            new BasicAuthenticationHttpInvokerProxyRequestExecutor(
                username, password, serviceUrl
            )
        );

        setServiceUrl(serviceUrl);
    }

    private static class BasicAuthenticationHttpInvokerProxyRequestExecutor extends HttpComponentsHttpInvokerRequestExecutor {
        private final HttpHost httpHost;
        private final HttpClientContext context;

        private BasicAuthenticationHttpInvokerProxyRequestExecutor(String username, String password, String serviceUrl) throws ClientProtocolException {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

            AuthCache authCache = new BasicAuthCache();
            BasicScheme basicAuth =  new BasicScheme();
            httpHost = determineTarget(URI.create(serviceUrl));
            authCache.put(httpHost, basicAuth);

            context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);
        }

        @Override
        protected HttpResponse executeHttpPost(HttpInvokerClientConfiguration config, HttpClient httpClient, HttpPost httpPost) throws IOException {
            return httpClient.execute(httpHost, httpPost, context);
        }

        private static HttpHost determineTarget(final URI requestURI) throws ClientProtocolException {
            HttpHost target = null;

            if (requestURI.isAbsolute()) {
                target = URIUtils.extractHost(requestURI);
                if (target == null) {
                    throw new ClientProtocolException("URI does not specify a valid host name: "
                        + requestURI);
                }
            }
            return target;
        }
    }
}
