package com.server.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.cloud.sdk.dto.Result;
import com.server.cloud.sdk.entity.User;
import com.server.cloud.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 用户信息处理
 * @Auther sea-hibn
 * @Date 2019-05-25
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public Result<List<User>> userAll(@RequestParam(defaultValue = "1") @Range(min=1) Integer page,
                          @RequestParam(defaultValue = "10") @Range(min = 5,max = 100) Integer size) throws Exception{
        // 测试自定义mapper
        List<User> minUsers = userService.listByAge(Integer.valueOf(10));
        log.info("自定义mapper测试：maxUsers == {}", JSONObject.toJSONString(minUsers));

        // 测试分页
        // 测试自定义查询语句+分页插件
        Page<User> paramPage = new Page<>(page.longValue(),size.longValue());
        IPage<User> resultPage = userService.selectPageVo(paramPage,Integer.valueOf(1));
        log.info("插件分页测试：resultPage == {}",JSONObject.toJSONString(resultPage));
        // 测试plus 插件分页
        IPage<User> resultPagePlus = userService.selectPageVoByPlus(paramPage,Integer.valueOf(1));
        log.info("插件分页测试：resultPagePlus == {}",JSONObject.toJSONString(resultPagePlus));
        // 测试通用mapper
        return Result.success(userService.getAll());
    }
}
