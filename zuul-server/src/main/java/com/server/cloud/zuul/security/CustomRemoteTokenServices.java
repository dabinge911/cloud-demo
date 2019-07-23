package com.server.cloud.zuul.security;

import com.server.cloud.zuul.consts.SecurityConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.server.cloud.sdk.consts.ServiceStatusEnum.*;

/**
 * @Description 自定义token认证处理
 * @Auther yanhb
 * @Date 2019/6/26
 */
public class CustomRemoteTokenServices implements ResourceServerTokenServices {
    private LoadBalancerClient loadBalancerClient;

    protected final Log logger = LogFactory.getLog(getClass());

    private RestOperations restTemplate;

    private String checkTokenEndpointUrl;

    private String clientId;

    private String clientSecret;

    private String tokenName = "token";

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    public CustomRemoteTokenServices() {
        restTemplate = new RestTemplate();
        ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }
            }
        });
    }

    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.tokenConverter = accessTokenConverter;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(1);
        formData.add(tokenName, accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));

        ServiceInstance serviceInstance = loadBalancerClient.choose(SecurityConstants.AUTH_SERVICE);
        if (serviceInstance == null) {
            throw new InvalidTokenException(String.valueOf(SERVICE_UNAVAILABLE_AUTH.code));
        }

        Map<String, Object> map = postForMap(serviceInstance.getUri().toString() + checkTokenEndpointUrl, formData, headers);

        if (map.containsKey("error")) {
            logger.debug("check_token returned error: " + map.get("error"));
            throw new InvalidTokenException(String.valueOf(NOT_TOKEN.code));
        }

        Assert.state(map.containsKey("client_id"), "Client id must be present in response from auth server");
        return tokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private String getAuthorizationHeader(String clientId, String clientSecret) {
        String creds = String.format("%s:%s", clientId, clientSecret);
        try {
            return new StringBuilder("Basic ").append(new String(Base64.encode(creds.getBytes("UTF-8")))).toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Could not convert String");
        }
    }

    private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers) throws InvalidTokenException {
        if (headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        try {
            @SuppressWarnings("rawtypes")
            Map map = restTemplate.exchange(path, HttpMethod.POST, new HttpEntity<>(formData, headers), Map.class).getBody();
            @SuppressWarnings("unchecked")
            Map<String, Object> result = map;
            return result;
        }catch (Exception e){
            throw new InvalidTokenException(String.valueOf(NOT_TOKEN.code));
        }
    }
}
