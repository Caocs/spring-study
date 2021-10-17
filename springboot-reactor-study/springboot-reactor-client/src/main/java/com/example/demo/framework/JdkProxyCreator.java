package com.example.demo.framework;

import com.example.demo.framework.bean.MethodInfo;
import com.example.demo.framework.bean.ServerInfo;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author caocs
 * @date 2021/10/1
 * <p>
 * 使用JDK动态代理实现代理类
 */
public class JdkProxyCreator implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {
        // 根据接口得到API服务器信息
        ServerInfo serverInfo = extractServerInfo(type);
        // 给每一个代理类，实现一个
        RestHandler handler = new WebClientRestHandler();
        // 初始化服务器信息（初始化webclient）
        handler.init(serverInfo);

        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{type},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 根据方法和参数，得到调用信息
                        MethodInfo methodInfo = extractMethodInfo(method, args);
                        // 调用rest
                        Object result = handler.invokeRest(methodInfo);
                        return result;
                    }

                    /**
                     * 根据方法定义和调用参数，得到调用的相关信息。
                     * @param method
                     * @param args
                     * @return
                     */
                    private MethodInfo extractMethodInfo(Method method, Object[] args) {
                        MethodInfo methodInfo = new MethodInfo();
                        // 得到请求URL和请求方法
                        Annotation[] annotations = method.getAnnotations();
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof GetMapping) {
                                GetMapping a = (GetMapping) annotation;
                                methodInfo.setUrl(a.value()[0]);
                                methodInfo.setMethod(HttpMethod.GET);
                            }
                            else if (annotation instanceof PostMapping) {
                                PostMapping a = (PostMapping) annotation;
                                methodInfo.setUrl(a.value()[0]);
                                methodInfo.setMethod(HttpMethod.POST);
                            }
                            else if (annotation instanceof DeleteMapping) {
                                DeleteMapping a = (DeleteMapping) annotation;
                                methodInfo.setUrl(a.value()[0]);
                                methodInfo.setMethod(HttpMethod.DELETE);
                            }
                        }

                        // 得到调用的参数和body
                        Parameter[] parameters = method.getParameters();
                        // 参数和值对应的map
                        Map<String,Object> params = new LinkedHashMap<>();
                        for (int i=0;i<parameters.length;i++){
                            // 是否带 @PathVariable
                            PathVariable annoPath = parameters[i].getAnnotation(PathVariable.class);
                            if(annoPath!=null){
                                params.put(annoPath.value(),args[i]);
                            }
                            // 是否带@RequestBody
                            RequestBody annoBody = parameters[i].getAnnotation(RequestBody.class);
                            if (annoBody!=null){
                                methodInfo.setBody((Mono<?>)args[i]);
                                // 泛型的真实类型
                                Type paramType = parameters[i].getParameterizedType();
                                Type[] actualTypeArguments = ((ParameterizedType)paramType).getActualTypeArguments();
                                Class clazz = (Class<?>)actualTypeArguments[0];
                                methodInfo.setBodyElementType(clazz);
                            }
                        }
                        methodInfo.setParams(params);

                        // 提取返回信息（是Flux或Mono、返回类型）
                        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
                        methodInfo.setReturnFlux(isFlux);
                        Type genericReturnType = method.getGenericReturnType();
                        Type[] actualTypeArguments = ((ParameterizedType)genericReturnType).getActualTypeArguments();
                        Class clazz = (Class<?>)actualTypeArguments[0];
                        methodInfo.setReturnElementType(clazz);


                        return methodInfo;
                    }
                }
        );
    }

    /**
     * 提取服务器信息
     *
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        Annotation annotation = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(((ApiServer) annotation).value());
        return serverInfo;
    }
}
