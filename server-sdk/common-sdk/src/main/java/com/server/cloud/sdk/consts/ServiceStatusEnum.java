package com.server.cloud.sdk.consts;

/**
 * @Description 服务状态
 * @Auther sea-hibn
 * @Date 2019-06-16
 */
public enum ServiceStatusEnum {
    OK(200,"successful"),
    ERROR_PARAMETER(300,"请求参数校验失败"),
    LOG_OUT(302,"退出登录失败"),
    NOT_LOGIN(410,"未登录"),
    NOT_TOKEN(411,"token令牌无效"),
    FAILURE(500,"服务器处理失败"),
    SERVICE_UNAVAILABLE(4001,"服务不可用"),
    SERVICE_UNAVAILABLE_AUTH(4002,"token校验服务不可用");


    public final int code;
    public final String msg;

    ServiceStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ServiceStatusEnum statusByCode(int code){
        for(ServiceStatusEnum statusEnum:ServiceStatusEnum.values()){
            if(statusEnum.code == code){
                return statusEnum;
            }
        }
        return null;
    }
}
