package com.server.cloud.zuul.config;

import com.server.cloud.zuul.handler.AuthExceptionEntryPoint;
import com.server.cloud.zuul.security.CustomRemoteTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * @Description 开启资源服务器
 * @Auther sea-hibn
 * @Date 2019-06-16
 */
@Configuration
@EnableResourceServer
public class SecurityConfigurer extends ResourceServerConfigurerAdapter {
    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Autowired
    private ResourceServerProperties resource;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private AuthExceptionEntryPoint authExceptionEntryPoint;

    /**
     * 白名单配置
     */
    private static final String[] AUTH_WHITELIST = {
//            "/feign/**",
            "/uaa/oauth/token",
            "swagger-resources/configuration/ui"
    };

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("app");
        resources.expressionHandler(expressionHandler);
        resources.authenticationEntryPoint(authExceptionEntryPoint);

        CustomRemoteTokenServices resourceServerTokenServices  = new CustomRemoteTokenServices();
        resourceServerTokenServices.setCheckTokenEndpointUrl(resource.getTokenInfoUri());
        resourceServerTokenServices.setClientId(resource.getClientId());
        resourceServerTokenServices.setClientSecret(resource.getClientSecret());
        resourceServerTokenServices.setLoadBalancerClient(loadBalancerClient);
        resources.tokenServices(resourceServerTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext){
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
}
