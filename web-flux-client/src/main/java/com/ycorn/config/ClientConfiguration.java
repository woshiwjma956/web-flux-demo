package com.ycorn.config;

import com.ycorn.proxy.ProxyCreator;
import com.ycorn.proxy.impl.JdkProxyCreator;
import com.ycorn.service.UserClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 16:35
 * @Function:
 * @Version 1.0
 */
@Configuration
public class ClientConfiguration {
    @Bean
    public ProxyCreator proxyCreator() {
        return new JdkProxyCreator();
    }

    @Bean
    FactoryBean<UserClient> userClient(ProxyCreator proxyCreator) {
        return new FactoryBean<UserClient>() {
            @Override
            public UserClient getObject() throws Exception {
                return (UserClient) proxyCreator.getProxy(UserClient.class);
            }

            @Override
            public Class<?> getObjectType() {
                return UserClient.class;
            }
        };
    }
}
