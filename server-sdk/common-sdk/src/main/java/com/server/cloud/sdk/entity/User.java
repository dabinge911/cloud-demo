package com.server.cloud.sdk.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 用户信息
 * @Auther sea-hibn
 * @Date 2019-05-25
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
}
