package com.example.demo.configuration;

import com.example.demo.aspect.WebClientInfo;
import com.example.demo.aspect.MethodExtract;
import com.example.demo.client.IUserWebClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author caocs
 * @date 2021/10/2
 */
@Configuration
public class ReactorWebClientBean {

    @Bean
    FactoryBean<IUserWebClient> userWebClientFactoryBean() {
        return new FactoryBean<IUserWebClient>() {
            @Override
            public IUserWebClient getObject() throws Exception {
                // 使用JDK动态代理方式，实现对WebClientApi注解解析并通过WebClient方式执行请求。
                return (IUserWebClient) Proxy.newProxyInstance(
                        this.getClass().getClassLoader(),
                        new Class[]{this.getObjectType()},
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                // 1、从method中提取信息
                                WebClientInfo webClientInfo = MethodExtract.getWebClientInfo(method, args);
                                // 2、根据提取信息，执行真正需要被代理的功能。
                                return MethodExtract.invokeRest(webClientInfo);
                            }
                        });
            }

            @Override
            public Class<?> getObjectType() {
                return IUserWebClient.class;
            }
        };
    }

}
