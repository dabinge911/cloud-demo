package com.server.cloud.sdk.exception.custom;

import com.server.cloud.sdk.consts.ServiceStatusEnum;
import lombok.Getter;

/**
 * @Description 自定义异常
 * @Auther sea-hibn
 * @Date 2019-07-05
 */
@Getter
public class CustomException extends RuntimeException{
    private ServiceStatusEnum status;

    public CustomException(String message, ServiceStatusEnum status) {
        super(message);
        this.status = status;
    }
}
