package com.server.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.cloud.sdk.entity.User;

import java.util.List;

/**
 * @Description 用户信息
 * @Auther sea-hibn
 * @Date 2019-05-25
 */
public interface IUserService {
    List<User> getAll() throws Exception;

    List<User> listByAge(Integer minAge) throws Exception;

    IPage<User> selectPageVo(Page<User> page,Integer sex) throws Exception;

    IPage<User> selectPageVoByPlus(Page<User> page,Integer sex) throws Exception;
}
