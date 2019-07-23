package com.server.cloud.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.cloud.dao.UserMapper;
import com.server.cloud.sdk.entity.User;
import com.server.cloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Auther sea-hibn
 * @Date 2019-05-25
 */

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * mybatis-plus 通用 mapper
     * @return
     */
    @Override
    public List<User> getAll() throws Exception{
        return userMapper.selectList(null);
    }

    /**
     * 使用自己编写的mapper文件
     * @param minAge
     * @return
     */
    @Override
    public List<User> listByAge(Integer minAge) throws Exception{
        return userMapper.listByAge(minAge);
    }

    /**
     * 自定义查询语句+分页插件
     * @param page
     * @param sex
     * @return
     */
    @Override
    public IPage<User> selectPageVo(Page<User> page, Integer sex) throws Exception{
        return userMapper.selectPageVo(page,sex);
    }

    /**
     * 通用mapper+分页插件
     * @param page
     * @param sex
     * @return
     */
    @Override
    public IPage<User> selectPageVoByPlus(Page<User> page, Integer sex) throws Exception{
        return userMapper.selectPage(page, Wrappers.<User>lambdaQuery().eq(User::getSex,sex));
    }
}
