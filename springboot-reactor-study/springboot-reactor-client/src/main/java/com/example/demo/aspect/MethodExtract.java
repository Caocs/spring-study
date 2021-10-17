package com.example.demo.aspect;

import com.example.demo.aspect.WebClientApi;
import com.example.demo.aspect.WebClientInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author caocs
 * @date 2021/10/2
 */
public class MethodExtract {

    /**
     * @param method 方法体信息
     * @param args   方法中的参数值
     * @return 根据方法体信息，得到这个方法所代表的的WebClient请求的含义
     */
    public static WebClientInfo getWebClientInfo(Method method, Object[] args) {
        WebClientInfo webClientInfo = new WebClientInfo();
        // method上的注解
        Annotation annotation = method.getAnnotation(WebClientApi.class);
        webClientInfo.setBaseUrl(((WebClientApi) annotation).baseUrl());
        webClientInfo.setRestUrl(((WebClientApi) annotation).restUrl());
        webClientInfo.setHttpMethod(((WebClientApi) annotation).httpMethod());
        // 得到调用的参数和body
        Parameter[] parameters = method.getParameters();
        Map<String, Object> params = new LinkedHashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            // 是否带 @PathVariable
            PathVariable annoPath = parameters[i].getAnnotation(PathVariable.class);
            if (annoPath != null) {
                params.put(annoPath.value(), args[i]);
            }
            // 是否带@RequestBody
            RequestBody annoBody = parameters[i].getAnnotation(RequestBody.class);
            if (annoBody != null) {
                webClientInfo.setBody((Mono<?>) args[i]);
                // 泛型的真实类型
                Type paramType = parameters[i].getParameterizedType();
                Type[] actualTypeArguments = ((ParameterizedType) paramType).getActualTypeArguments();
                Class clazz = (Class<?>) actualTypeArguments[0];
                webClientInfo.setBodyType(clazz);
            }
        }
        webClientInfo.setParams(params);
        // 提取返回信息（是Flux或Mono、返回类型）
        Class<?> returnType = method.getReturnType();
        boolean isMono = returnType.isAssignableFrom(Mono.class);
        boolean isFlux = returnType.isAssignableFrom(Flux.class);
        if (isMono) {
            webClientInfo.setReturnType(1);
        } else if (isFlux) {
            webClientInfo.setReturnType(2);
        } else {
            webClientInfo.setReturnType(0);
        }
        Type genericReturnType = method.getGenericReturnType();
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        Class clazz = (Class<?>) actualTypeArguments[0];
        webClientInfo.setReturnActualType(clazz);
        return webClientInfo;
    }


    public static Object invokeRest(WebClientInfo webClientInfo) {
        Object result = null;
        WebClient.RequestBodySpec requestBodySpec
                = WebClient.create(webClientInfo.getBaseUrl())
                .method(webClientInfo.getHttpMethod())
                .uri(webClientInfo.getRestUrl(), webClientInfo.getParams())
                .accept(MediaType.APPLICATION_JSON);
        // 处理请求中RequestBody
        WebClient.ResponseSpec responseSpec = null;
        if (webClientInfo.getBody() != null) {
            responseSpec = requestBodySpec
                    .body(webClientInfo.getBody(), webClientInfo.getBodyType())
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
        if (webClientInfo.getReturnType() == 2) {
            result = responseSpec.bodyToFlux(webClientInfo.getReturnActualType());
        } else if (webClientInfo.getReturnType() == 1) {
            result = responseSpec.bodyToMono(webClientInfo.getReturnActualType());
        }
        return result;
    }

}
