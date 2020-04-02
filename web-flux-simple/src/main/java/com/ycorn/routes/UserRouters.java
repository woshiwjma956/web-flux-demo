package com.ycorn.routes;

import com.sun.corba.se.spi.activation.Server;
import com.ycorn.entity.User;
import com.ycorn.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 16:49
 * @Function:
 * @Version 1.0
 */
@Configuration
public class UserRouters {


    @Bean
    public RouterFunction<ServerResponse> curdUserRoute(UserHandler userHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/user"),
                RouterFunctions.route(RequestPredicates.GET("/findAll"), userHandler::findAll)
                        .and(RouterFunctions.route(RequestPredicates.GET("/findOne"), userHandler::findOne)));
    }
}
