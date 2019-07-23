package com.server.cloud.sdk.exception.advice;

import com.server.cloud.sdk.consts.ServiceStatusEnum;
import com.server.cloud.sdk.dto.Result;
import com.server.cloud.sdk.exception.custom.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.ValidationException;

/**
 * @Description controller全局未捕获异常的处理
 * @Auther sea-hibn
 * @Date 2019-07-05
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e){

        Result result;
        if(e instanceof ValidationException){
            result = Result.failure(ServiceStatusEnum.ERROR_PARAMETER,e.getMessage());
        } else if(e instanceof CustomException){ //自定义异常处理
            CustomException cu_ex = (CustomException) e;
            result = Result.failure(cu_ex.getStatus(),cu_ex.getMessage());
        } else{
            String ex_msg = new StringBuilder(e.getClass().getName())
                    .append(": ")
                    .append(e.getMessage())
                    .toString();
            result = Result.failure(ServiceStatusEnum.FAILURE,ex_msg);
            //错误日志打印 -- 异步执行
            StringBuilder sOut = new StringBuilder("\t"+ex_msg+"\r\n");
            StackTraceElement[] trace = e.getStackTrace();
            for(StackTraceElement s : trace){
                sOut.append("\tat " + s + "\r\n");
            }
            log.error(sOut.toString());

        }
        return result;
    }
}
