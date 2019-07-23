package com.server.cloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.server.cloud.sdk.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @Auther sea-hibn
 * @Date 2019-05-25
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> listByAge(@Param("minAge") Integer minAge);

    /**
     * 测试自定义查询语句+分页插件
     * @param page
     * @param sex
     * @return
     */
    @Select("select * from user where sex = #{sex}")
    IPage<User> selectPageVo(Page page,@Param("sex") Integer sex);
}
