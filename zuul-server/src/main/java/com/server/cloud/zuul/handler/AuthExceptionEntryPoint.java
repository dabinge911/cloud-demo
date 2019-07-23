package com.server.cloud.zuul.handler;

import com.alibaba.fastjson.JSON;
import com.server.cloud.sdk.consts.ServiceStatusEnum;
import com.server.cloud.sdk.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description 定义访问资源token认证失败返回异常格式定义访问资源token认证失败返回异常格式
 * @Auther yanhb
 * @Date 2019/7/2
 */
@Slf4j
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ServiceStatusEnum statusEnum;
        String ex_msg = e.getMessage();

        try {
            int ex_code = Integer.valueOf(ex_msg);
            statusEnum = ServiceStatusEnum.statusByCode(ex_code);
        }catch (Exception ex){
            if(e instanceof InsufficientAuthenticationException){
                statusEnum = ServiceStatusEnum.NOT_LOGIN;
            } else {
                statusEnum = ServiceStatusEnum.FAILURE;
                log.error("token 异常信息：{},{}", ex_msg, e.getClass().getName());
                // TODO 错误日志记录
            }
        }

        Result body = Result.failure(statusEnum);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(JSON.toJSONString(body));
    }
}
