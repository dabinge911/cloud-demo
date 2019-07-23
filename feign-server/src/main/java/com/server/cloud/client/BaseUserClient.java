package com.server.cloud.client;

import com.server.cloud.client.hystrix.BaseUserHystrix;
import com.server.cloud.sdk.consts.FeignServerIdConsts;
import com.server.cloud.sdk.dto.Result;
import com.server.cloud.sdk.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description feign远程调用
 * @Auther sea-hibn
 * @Date 2019-05-27
 */
@FeignClient(value = FeignServerIdConsts.BASEUSER_SERVER,fallback = BaseUserHystrix.class)
public interface BaseUserClient {
    @GetMapping("/user/all")
    Result<List<User>> userAll(@RequestParam("page")Integer page, @RequestParam("size") Integer size);
}
