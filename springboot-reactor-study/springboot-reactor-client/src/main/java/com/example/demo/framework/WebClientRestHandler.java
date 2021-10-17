package com.example.demo.framework;

import com.example.demo.framework.bean.MethodInfo;
import com.example.demo.framework.bean.ServerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author caocs
 * @date 2021/10/1
 */
public class WebClientRestHandler implements RestHandler {

    private WebClient webClient;

    /**
     * 初始化WebClient
     *
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.webClient = WebClient.create(serverInfo.getUrl());
    }

    /**
     * 处理rest请求
     *
     * @param methodInfo
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        Object result = null;
        WebClient.RequestBodySpec requestBodySpec = this.webClient
                .method(methodInfo.getMethod())
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                .accept(MediaType.APPLICATION_JSON);
        // 处理请求中RequestBody
        WebClient.ResponseSpec responseSpec = null;
        if (methodInfo.getBody() != null) {
            responseSpec = requestBodySpec
                    .body(methodInfo.getBody(), methodInfo.getBodyElementType())
                    .retrieve(); // 发送请求
        } else {
            responseSpec = requestBodySpec.retrieve();
        }

        // 处理异常
        responseSpec.onStatus(
                httpStatus -> httpStatus.value() == 404,
                response -> Mono.just(new Exception("我出问题了，你快来看看"))
        );

        // 处理返回body类型
        if (methodInfo.isReturnFlux()) {
            result = responseSpec.bodyToFlux(methodInfo.getReturnElementType());
        } else {
            result = responseSpec.bodyToMono(methodInfo.getReturnElementType());
        }
        return result;
    }
}
