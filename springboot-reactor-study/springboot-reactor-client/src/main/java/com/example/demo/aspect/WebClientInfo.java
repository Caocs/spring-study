package com.example.demo.aspect;

import lombok.Data;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author caocs
 * @date 2021/10/2
 *
 * 异步请求
 */
@Data
public class WebClientInfo {
    /**
     * 请求服务地址url
     */
    private String baseUrl;
    /**
     * 请求服务中rest路由
     */
    private String restUrl;
    /**
     * 请求方法
     */
    private HttpMethod httpMethod;
    /**
     * 请求路由上带的参数
     */
    private Map<String, Object> params;
    /**
     * 请求体
     * 由于异步请求，这里只支持Mono<?>
     */
    private Mono<?> body;
    /**
     * 请求体body的类型
     */
    private Class<?> bodyType;
    /**
     * 0：一般类型
     * 1：Mono
     * 2：Flux
     */
    private int returnType;

    /**
     * 返回对象的真实类型
     */
    private Class<?> returnActualType;

}
