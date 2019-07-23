package com.server.cloud.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description 必须要有，做验证
 * @Auther yanhb
 * @Date 2019/6/25
 */
@RestController
public class UserController {
    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
