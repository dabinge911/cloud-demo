package com.server.cloud.client.hystrix;

import com.server.cloud.client.BaseUserClient;
import com.server.cloud.sdk.consts.ServiceStatusEnum;
import com.server.cloud.sdk.dto.Result;
import com.server.cloud.sdk.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 熔断器
 * @Auther sea-hibn
 * @Date 2019-05-27
 */
@Component
public class BaseUserHystrix implements BaseUserClient {
    @Override
    public Result<List<User>> userAll(Integer page, Integer size) {
        return Result.failure(ServiceStatusEnum.SERVICE_UNAVAILABLE);
    }
}
