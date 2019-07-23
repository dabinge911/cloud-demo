package com.server.cloud.sdk.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.cloud.sdk.consts.ServiceStatusEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 统一返回数据格式封装类
 * @Auther sea-hibn
 * @Date 2019-06-16
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    /**
     * 处理状态码
     */
    private int code = ServiceStatusEnum.OK.code;
    /**
     * 处理服务状态码消息
     */
    private String msg = ServiceStatusEnum.OK.msg;
    /**
     * 服务处理详细描述
     */
    private String description;
    /**
     * 服务处理结果数据
     */
    private T data;
    /**
     * 其他参数处理
     */
    private Map<String,Object> exend;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Map<String, Object> getExend() {
        return exend;
    }

    public void setExend(Map<String, Object> exend) {
        this.exend = exend;
    }

    @JsonIgnore
    public boolean isOk(){
        return code == ServiceStatusEnum.OK.code;
    }

    @JsonIgnore
    public boolean isFailed(){
        return !isOk();
    }

    @JsonIgnore
    public boolean noData(){
        return isFailed() || data == null;
    }

    public Result() {
        exend = new HashMap<>();
    }

    public static Result failure(ServiceStatusEnum statusEnum) {
        return failure(statusEnum,null);
    }

    public static Result failure(ServiceStatusEnum statusEnum,String description) {
        Result result = new Result();
        result.setCode(statusEnum.code);
        result.setMsg(statusEnum.msg);
        result.setDescription(description);
        return result;
    }

    public static Result success() {
        return new Result();
    }

    public static Result success(Object data){
        return success(data,null);
    }

    public static Result success(Object data,Map<String, Object> map){
        Result result = new Result();
        result.setData(data);
        if(map != null && !map.isEmpty()) {
            result.exend.putAll(map);
        }
        return result;
    }

    public Result put(String key, Object value) {
        exend.put(key, value);
        return this;
    }
}
