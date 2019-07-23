package com.server.cloud.auth.config;

import com.server.cloud.auth.config.custom.CustomJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Description 认证服务器配置
 * @Auther sea-hibn
 * @Date 2019-06-27
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许所有人请求token
        // 只有认证客户端才能请求check_token端点(check_token:用于资源服务访问的令牌解析端点)
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalSecret = new BCryptPasswordEncoder().encode("app");
        clients.inMemory()
                    .withClient("app")
                    .secret(finalSecret)
                    .authorizedGrantTypes("authorization_code", "password")
                    .scopes("app");
    }

    /**
     * 配置授权模式、令牌、
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore());
//                .accessTokenConverter(accessTokenConverter());
    }

    /**
     * 使用Jwt的方式生成token
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        CustomJwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
        converter.setSigningKey("secret");
        return converter;
    }
}
