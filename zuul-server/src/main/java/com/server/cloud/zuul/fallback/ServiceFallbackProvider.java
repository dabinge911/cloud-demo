package com.server.cloud.zuul.fallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.cloud.sdk.consts.ServiceStatusEnum;
import com.server.cloud.sdk.dto.FallbackErrorBody;
import com.server.cloud.sdk.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 服务调用失败回退处理
 * @Auther sea-hibn
 * @Date 2019-06-16
 */
@Slf4j
@Component
public class ServiceFallbackProvider implements FallbackProvider {
    /**
     * 表明是为哪个微服务提供回退，* 代表全部
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                StringBuilder errorMsg = new StringBuilder("服务：")
                        .append(route)
                        .append("，不可用");
                Result body = Result.failure(ServiceStatusEnum.SERVICE_UNAVAILABLE,errorMsg.toString());
                ObjectMapper objectMapper = new ObjectMapper();
                String context = objectMapper.writeValueAsString(body);
                return new ByteArrayInputStream(context.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return httpHeaders;
            }
        };
    }
}
