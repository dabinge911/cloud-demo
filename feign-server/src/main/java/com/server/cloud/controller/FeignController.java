package com.server.cloud.controller;

import com.server.cloud.client.BaseUserClient;
import com.server.cloud.sdk.dto.Result;
import com.server.cloud.sdk.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Auther sea-hibn
 * @Date 2019-05-27
 */

@Slf4j
@RestController
@RequestMapping("/feign")
public class FeignController {
    @Autowired
    private BaseUserClient baseUserClient;

    @GetMapping("/userall")
    public Result<List<User>> userAll(){
        return baseUserClient.userAll(1,10);
    }
}
