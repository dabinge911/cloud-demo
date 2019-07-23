package com.server.cloud.sdk.dto;

import com.server.cloud.sdk.consts.ServiceStatusEnum;

import java.io.Serializable;

/**
 * @Description 服务调用异常
 * @Auther sea-hibn
 * @Date 2019-06-16
 */
public class FallbackErrorBody<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public FallbackErrorBody() {
    }

    public FallbackErrorBody(ServiceStatusEnum statusEnum){
        this.code = statusEnum.code;
        this.msg = statusEnum.msg;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
