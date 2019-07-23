package com.server.cloud.dbsdk.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description 配置mybatis分页插件
 * @Auther sea-hibn
 * @Date 2019-05-25
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.server.cloud.dao")
public class MybatisPlusConfig {

    /**
     * mybatis分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
