package com.server.cloud.zuul.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 匹配url
 * @Auther sea-hibn
 * @Date 2019-06-16
 */
public interface PermissionService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
