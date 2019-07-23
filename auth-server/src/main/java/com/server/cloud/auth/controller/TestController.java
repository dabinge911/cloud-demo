package com.server.cloud.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 测试controller
 * @Auther sea-hibn
 * @Date 2019-06-23
 */
@RestController
public class TestController {
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id:"+id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id){
        return "order id:"+id;
    }
}
