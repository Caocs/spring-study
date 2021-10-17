package com.example.demo.configuration;

import com.example.demo.client.IUserClient;
import com.example.demo.framework.JdkProxyCreator;
import com.example.demo.framework.ProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caocs
 * @date 2021/10/1
 */
@Configuration
public class BeanInit {
    @Bean
    ProxyCreator jdkProxyCreator() {
        return new JdkProxyCreator();
    }

    @Bean
    FactoryBean<IUserClient> userClientFactoryBean(ProxyCreator proxyCreator) {
        return new FactoryBean<IUserClient>() {
            @Override
            public IUserClient getObject() throws Exception {
                return (IUserClient) proxyCreator.createProxy(this.getObjectType());
            }

            @Override
            public Class<?> getObjectType() {
                return IUserClient.class;
            }
        };
    }

}
